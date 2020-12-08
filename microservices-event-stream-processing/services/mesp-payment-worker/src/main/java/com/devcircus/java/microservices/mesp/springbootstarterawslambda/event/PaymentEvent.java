package com.devcircus.java.microservices.mesp.springbootstarterawslambda.event;

import demo.domain.BaseEntity;

public class PaymentEvent extends BaseEntity {

    private PaymentEventType type;

    public PaymentEvent() {
    }

    public PaymentEvent(PaymentEventType type) {
        this.type = type;
    }

    public PaymentEventType getType() {
        return type;
    }

    public void setType(PaymentEventType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PaymentEvent{"
                + "type=" + type
                + "} " + super.toString();
    }
}
