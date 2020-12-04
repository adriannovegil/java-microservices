package demo.warehouse.domain;

import demo.order.domain.Order;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

public class Warehouses extends Resources<Order> {

    public Warehouses() {
    }

    public Warehouses(Iterable<Order> content, Link... links) {
        super(content, links);
    }
}
