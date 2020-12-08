package com.devcircus.java.microservices.mesp.warehouseworker.warehouse.domain;

import com.devcircus.java.microservices.mesp.warehouseworker.domain.AbstractEntity;
import com.devcircus.java.microservices.mesp.warehouseworker.warehouse.event.WarehouseEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

public class Warehouse extends AbstractEntity {

    private Long id;
    private List<WarehouseEvent> events = new ArrayList<>();
    private Address address;
    private WarehouseStatus status;

    public Warehouse() {
    }

    public Warehouse(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public WarehouseStatus getStatus() {
        return status;
    }

    public void setStatus(WarehouseStatus status) {
        this.status = status;
    }

    @JsonProperty("warehouseId")
    public Long getIdentity() {
        return id;
    }

    public void setIdentity(Long id) {
        this.id = id;
    }

    public List<WarehouseEvent> getEvents() {
        return events;
    }

    public void setEvents(List<WarehouseEvent> events) {
        this.events = events;
    }

    @Override
    public Link getId() {
        return getLink("self");
    }

}
