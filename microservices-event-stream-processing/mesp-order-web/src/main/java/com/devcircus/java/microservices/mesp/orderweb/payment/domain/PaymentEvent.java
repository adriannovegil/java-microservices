package demo.payment.domain;

import demo.event.Event;

public class PaymentEvent extends Event<Payment, PaymentEventType, Long> {

    private Long eventId;
    private PaymentEventType type;
    private Payment payment;
    private Long createdAt;
    private Long lastModified;

    public PaymentEvent() {
    }

    public PaymentEvent(PaymentEventType type) {
        this.type = type;
    }

    public PaymentEvent(PaymentEventType type, Payment payment) {
        this.type = type;
        this.payment = payment;
    }

    @Override
    public Long getEventId() {
        return eventId;
    }

    @Override
    public void setEventId(Long id) {
        eventId = id;
    }

    @Override
    public PaymentEventType getType() {
        return type;
    }

    @Override
    public void setType(PaymentEventType type) {
        this.type = type;
    }

    @Override
    public Payment getEntity() {
        return payment;
    }

    @Override
    public void setEntity(Payment entity) {
        this.payment = entity;
    }

    @Override
    public Long getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Long getLastModified() {
        return lastModified;
    }

    @Override
    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }
}
