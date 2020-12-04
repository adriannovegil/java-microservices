package demo.order.domain;

import demo.domain.Service;
import demo.order.event.OrderEvent;
import demo.order.event.OrderEventType;
import demo.order.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@org.springframework.stereotype.Service
public class OrderService extends Service<Order, Long> {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order registerOrder(Order order) {

        order = create(order);

        // Trigger the order creation event
        order.sendAsyncEvent(new OrderEvent(OrderEventType.ORDER_CREATED, order));

        return order;
    }

    public Order create(Order order) {

        // Save the order to the repository
        order = orderRepository.saveAndFlush(order);

        return order;
    }

    public Order get(Long id) {
        return orderRepository.findOne(id);
    }

    @Transactional
    public Order update(Order order) {
        Assert.notNull(order.getIdentity(), "Order id must be present in the resource URL");
        Assert.notNull(order, "Order request body cannot be null");

        Assert.state(orderRepository.exists(order.getIdentity()),
                "The order with the supplied id does not exist");

        Order currentOrder = get(order.getIdentity());
        currentOrder.setAccountId(order.getAccountId());
        currentOrder.setPaymentId(order.getPaymentId());
        currentOrder.setLineItems(order.getLineItems());
        currentOrder.setShippingAddress(order.getShippingAddress());
        currentOrder.setStatus(order.getStatus());

        return orderRepository.saveAndFlush(currentOrder);
    }

    public boolean delete(Long id) {
        Assert.state(orderRepository.exists(id),
                "The order with the supplied id does not exist");
        this.orderRepository.delete(id);
        return true;
    }

    public Orders findOrdersByAccountId(Long accountId) {
        return new Orders(orderRepository.findOrdersByAccountId(accountId));
    }
}
