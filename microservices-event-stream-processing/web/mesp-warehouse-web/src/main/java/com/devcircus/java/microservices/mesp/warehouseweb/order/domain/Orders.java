package com.devcircus.java.microservices.mesp.warehouseweb.order.domain;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

public class Orders extends Resources<Order> {

    public Orders() {
    }

    public Orders(Iterable<Order> content, Link... links) {
        super(content, links);
    }
}
