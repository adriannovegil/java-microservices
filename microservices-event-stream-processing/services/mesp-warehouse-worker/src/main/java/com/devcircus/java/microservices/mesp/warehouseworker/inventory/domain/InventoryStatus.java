package com.devcircus.java.microservices.mesp.warehouseworker.inventory.domain;

public enum InventoryStatus {
    INVENTORY_CREATED,
    RESERVATION_PENDING,
    RESERVATION_CONNECTED,
    INVENTORY_RESERVED,
    INVENTORY_RELEASED
}
