package com.devcircus.java.microservices.mesp.warehouseworker.inventory.event;

import demo.domain.AbstractEntity;

public class InventoryEvent extends AbstractEntity {

    private InventoryEventType type;

    public InventoryEvent() {
    }

    public InventoryEvent(InventoryEventType type) {
        this.type = type;
    }

    public InventoryEventType getType() {
        return type;
    }

    public void setType(InventoryEventType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "InventoryEvent{"
                + "type=" + type
                + "} " + super.toString();
    }
}
