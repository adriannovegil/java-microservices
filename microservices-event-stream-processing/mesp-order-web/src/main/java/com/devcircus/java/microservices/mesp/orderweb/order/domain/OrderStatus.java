package com.devcircus.java.microservices.mesp.orderweb.order.domain;

public enum OrderStatus {
    ORDER_CREATED,
    ACCOUNT_CONNECTED,
    RESERVATION_PENDING,
    INVENTORY_RESERVED,
    RESERVATION_SUCCEEDED,
    RESERVATION_FAILED,
    PAYMENT_CREATED,
    PAYMENT_CONNECTED,
    PAYMENT_PENDING,
    PAYMENT_SUCCEEDED,
    PAYMENT_FAILED,
    ORDER_SUCCEEDED,
    ORDER_FAILED
}
