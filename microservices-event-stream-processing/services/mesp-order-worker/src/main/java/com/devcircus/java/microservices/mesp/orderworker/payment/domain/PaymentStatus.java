package com.devcircus.java.microservices.mesp.orderworker.payment.domain;

public enum PaymentStatus {
    PAYMENT_CREATED,
    ORDER_CONNECTED,
    PAYMENT_PENDING,
    PAYMENT_PROCESSED,
    PAYMENT_FAILED,
    PAYMENT_SUCCEEDED
}
