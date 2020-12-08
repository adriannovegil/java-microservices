package com.devcircus.java.microservices.mesp.loadsimulator.warehouse.domain;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

public class Warehouses extends Resources<Warehouse> {

    public Warehouses() {
    }

    public Warehouses(Iterable<Warehouse> content, Link... links) {
        super(content, links);
    }
}
