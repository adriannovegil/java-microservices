package com.devcircus.java.microservices.mesp.warehouseworker.order.domain;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

public class Orders extends Resources<Order> {

    public Orders() {
    }

    public Orders(Iterable<Order> content, Link... links) {
        super(content, links);
    }
}
