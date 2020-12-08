package com.devcircus.java.microservices.mes.cartservice.cart;

import com.devcircus.java.microservices.mes.cartservice.catalog.Catalog;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.log4j.Logger;
import reactor.core.publisher.Flux;
import java.util.*;
import java.util.stream.Collectors;

public class ShoppingCart {

    private Logger log = Logger.getLogger(ShoppingCart.class);
    private Map<String, Integer> productMap = new HashMap<>();
    private List<LineItem> lineItems = new ArrayList<>();
    private Catalog catalog;

    public ShoppingCart(Catalog catalog) {
        this.catalog = catalog;
    }

    @JsonIgnore
    public Map<String, Integer> getProductMap() {
        return productMap;
    }

    public void setProductMap(Map<String, Integer> productMap) {
        this.productMap = productMap;
    }

    @JsonIgnore
    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public List<LineItem> getLineItems() throws Exception {
        lineItems = productMap.entrySet()
                .stream()
                .map(item -> new LineItem(item.getKey(), catalog.getProducts().stream()
                .filter(prd -> Objects.equals(prd.getProductId(), item.getKey()))
                .findFirst()
                .orElse(null), item.getValue()))
                .filter(item -> item.getQuantity() > 0)
                .collect(Collectors.toList());

        if (lineItems.stream()
                .anyMatch(item -> item.getProduct() == null)) {
            throw new Exception("Product not found in catalog");
        }

        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public ShoppingCart incorporate(CartEvent cartEvent) {
        // Remember that thing about safety properties in microservices?
        Flux<CartEventType> validCartEventTypes
                = Flux.fromStream(Stream.of(CartEventType.ADD_ITEM,
                        CartEventType.REMOVE_ITEM));

        // The CartEvent's type must be either ADD_ITEM or REMOVE_ITEM
        if (validCartEventTypes.exists(cartEventType
                -> cartEvent.getCartEventType().equals(cartEventType)).get()) {
            // Update the aggregate view of each line item's quantity from the event type
            productMap.put(cartEvent.getProductId(),
                    productMap.getOrDefault(cartEvent.getProductId(), 0)
                    + (cartEvent.getQuantity() * (cartEvent.getCartEventType()
                    .equals(CartEventType.ADD_ITEM) ? 1 : -1)));
        }

        // Return the updated state of the aggregate to the reactive stream's reduce method
        return this;
    }

    public static Boolean isTerminal(CartEventType eventType) {
        return (eventType == CartEventType.CLEAR_CART || eventType == CartEventType.CHECKOUT);
    }

    @Override
    public String toString() {
        return "ShoppingCart{"
                + "productMap=" + productMap
                + ", catalog=" + catalog
                + ", lineItems=" + lineItems
                + '}';
    }
}
