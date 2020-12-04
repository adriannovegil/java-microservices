package demo.payment.domain;

import demo.order.domain.Order;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

public class Payments extends Resources<Order> {

    public Payments() {
    }

    public Payments(Iterable<Order> content, Link... links) {
        super(content, links);
    }
}
