package com.devcircus.java.microservices.mesp.paymentweb.payment.event;

public enum PaymentEventType {
    PAYMENT_CREATED,
    ORDER_CONNECTED,
    PAYMENT_PENDING,
    PAYMENT_PROCESSED,
    PAYMENT_FAILED,
    PAYMENT_SUCCEEDED
}
