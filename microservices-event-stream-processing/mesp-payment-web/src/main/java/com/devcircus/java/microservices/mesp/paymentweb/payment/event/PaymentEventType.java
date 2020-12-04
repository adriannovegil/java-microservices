package demo.payment.event;

import demo.payment.domain.Payment;
import demo.payment.domain.PaymentStatus;

public enum PaymentEventType {
    PAYMENT_CREATED,
    ORDER_CONNECTED,
    PAYMENT_PENDING,
    PAYMENT_PROCESSED,
    PAYMENT_FAILED,
    PAYMENT_SUCCEEDED
}
