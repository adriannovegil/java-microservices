package com.devcircus.java.microservices.mesp.warehouseworker.inventory.event;

public enum InventoryEventType {
    INVENTORY_CREATED,
    RESERVATION_PENDING,
    RESERVATION_CONNECTED,
    INVENTORY_RESERVED,
    INVENTORY_RELEASED
}
