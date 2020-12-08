package com.devcircus.java.microservices.mes.cartservice.api.v1;

import demo.cart.*;
import demo.catalog.Catalog;
import demo.inventory.Inventory;
import demo.order.Order;
import demo.order.OrderEvent;
import demo.order.OrderEventType;
import demo.user.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Service
public class ShoppingCartServiceV1 {

    public static final double TAX = .06;
    private final Log log = LogFactory.getLog(ShoppingCartServiceV1.class);
    private OAuth2RestTemplate oAuth2RestTemplate;
    private CartEventRepository cartEventRepository;
    private RestTemplate restTemplate;

    @Autowired
    public ShoppingCartServiceV1(CartEventRepository cartEventRepository,
            @LoadBalanced OAuth2RestTemplate oAuth2RestTemplate,
            @LoadBalanced RestTemplate normalRestTemplate) {
        this.cartEventRepository = cartEventRepository;
        this.oAuth2RestTemplate = oAuth2RestTemplate;
        this.restTemplate = normalRestTemplate;
    }

    public User getAuthenticatedUser() {
        return oAuth2RestTemplate.getForObject("http://user-service/uaa/v1/me", User.class);
    }

    public Boolean addCartEvent(CartEvent cartEvent) {
        User user = getAuthenticatedUser();
        if (user != null) {
            cartEvent.setUserId(user.getId());
            cartEventRepository.save(cartEvent);
        } else {
            return null;
        }
        return true;
    }

    public Boolean addCartEvent(CartEvent cartEvent, User user) {
        if (user != null) {
            cartEvent.setUserId(user.getId());
            cartEventRepository.save(cartEvent);
        } else {
            return null;
        }
        return true;
    }

    public ShoppingCart getShoppingCart() throws Exception {
        User user = oAuth2RestTemplate.getForObject("http://user-service/uaa/v1/me", User.class);
        ShoppingCart shoppingCart = null;
        if (user != null) {
            Catalog catalog = restTemplate.getForObject("http://catalog-service/v1/catalog", Catalog.class);
            shoppingCart = aggregateCartEvents(user, catalog);
        }
        return shoppingCart;
    }

    public ShoppingCart aggregateCartEvents(User user, Catalog catalog) throws Exception {
        Flux<CartEvent> cartEvents
                = Flux.fromStream(cartEventRepository.getCartEventStreamByUser(user.getId()));

        // Aggregate the state of the shopping cart
        ShoppingCart shoppingCart = cartEvents
                .takeWhile(cartEvent -> !ShoppingCart.isTerminal(cartEvent.getCartEventType()))
                .reduceWith(() -> new ShoppingCart(catalog), ShoppingCart::incorporate)
                .get();

        shoppingCart.getLineItems();

        return shoppingCart;
    }

    public CheckoutResult checkout() throws Exception {
        CheckoutResult checkoutResult = new CheckoutResult();

        // Check available inventory
        ShoppingCart currentCart = null;
        try {
            currentCart = getShoppingCart();
        } catch (Exception e) {
            log.error("Could not retrieve shopping cart", e);
        }

        if (currentCart != null) {
            // Reconcile the current cart with the available inventory
            Inventory[] inventory
                    = oAuth2RestTemplate.getForObject(String.format("http://inventory-service/v1/inventory?productIds=%s", currentCart.getLineItems()
                            .stream()
                            .map(LineItem::getProductId)
                            .collect(Collectors.joining(","))), Inventory[].class);

            if (inventory != null) {
                Map<String, Long> inventoryItems = Arrays.asList(inventory)
                        .stream()
                        .map(inv -> inv.getProduct().getProductId())
                        .collect(groupingBy(Function.identity(), counting()));

                if (checkAvailableInventory(checkoutResult, currentCart, inventoryItems)) {
                    // Reserve the available inventory

                    // Create a new order
                    Order orderResponse = oAuth2RestTemplate.postForObject("http://order-service/v1/orders",
                            currentCart.getLineItems().stream()
                                    .map(prd
                                            -> new demo.order.LineItem(prd.getProduct().getName(),
                                            prd.getProductId(), prd.getQuantity(),
                                            prd.getProduct().getUnitPrice(), TAX))
                                    .collect(Collectors.toList()),
                            Order.class);

                    if (orderResponse != null) {
                        // Order creation successful
                        checkoutResult.setResultMessage("Order created");

                        // Add order event
                        oAuth2RestTemplate.postForEntity(String.format("http://order-service/v1/orders/%s/events", orderResponse.getOrderId()),
                                new OrderEvent(OrderEventType.CREATED, orderResponse.getOrderId()), ResponseEntity.class);

                        checkoutResult.setOrder(orderResponse);
                    }

                    User user = oAuth2RestTemplate.getForObject("http://user-service/uaa/v1/me", User.class);

                    // Clear the shopping cart
                    addCartEvent(new CartEvent(CartEventType.CHECKOUT, user.getId()), user);
                }
            }
        }

        // Return errors with available inventory
        return checkoutResult;
    }

    public Boolean checkAvailableInventory(CheckoutResult checkoutResult, ShoppingCart currentCart,
            Map<String, Long> inventoryItems) {
        Boolean hasInventory = true;
        // Determine if inventory is available
        try {
            List<LineItem> inventoryAvailable = currentCart.getLineItems()
                    .stream()
                    .filter(item -> inventoryItems.get(item.getProductId()) - item.getQuantity() < 0)
                    .collect(Collectors.toList());
            if (inventoryAvailable.size() > 0) {
                String productIdList = inventoryAvailable
                        .stream()
                        .map(LineItem::getProductId)
                        .collect(Collectors.joining(", "));
                checkoutResult.setResultMessage(
                        String.format("Insufficient inventory available for %s. "
                                + "Lower the quantity of these products and try again.", productIdList));
                hasInventory = false;
            }
        } catch (Exception e) {
            log.error("Error checking for available inventory", e);
        }
        return hasInventory;
    }

}
