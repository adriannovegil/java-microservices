package com.devcircus.java.microservices.mesp.orderweb.payment.domain;

public enum PaymentEventType {
    PAYMENT_CREATED,
    ORDER_CONNECTED,
    PAYMENT_PENDING,
    PAYMENT_PROCESSED,
    PAYMENT_FAILED,
    PAYMENT_SUCCEEDED
}
