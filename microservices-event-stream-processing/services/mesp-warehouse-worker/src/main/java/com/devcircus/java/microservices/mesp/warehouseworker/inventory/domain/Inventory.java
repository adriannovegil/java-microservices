package com.devcircus.java.microservices.mesp.warehouseworker.inventory.domain;

import com.devcircus.java.microservices.mesp.warehouseworker.domain.AbstractEntity;
import com.devcircus.java.microservices.mesp.warehouseworker.inventory.event.InventoryEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

public class Inventory extends AbstractEntity {

    private Long id;
    private InventoryStatus status;
    private List<InventoryEvent> events = new ArrayList<>();
    private String productId;

    public Inventory() {
    }

    @JsonProperty("inventoryId")
    public Long getIdentity() {
        return this.id;
    }

    public void setIdentity(Long id) {
        this.id = id;
    }

    public InventoryStatus getStatus() {
        return status;
    }

    public void setStatus(InventoryStatus status) {
        this.status = status;
    }

    public List<InventoryEvent> getEvents() {
        return events;
    }

    public void setEvents(List<InventoryEvent> events) {
        this.events = events;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public Link getId() {
        return getLink("self");
    }
}
