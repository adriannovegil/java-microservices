package demo.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderEvent {

    private Long eventId;
    private OrderEventType type;
    @JsonIgnore
    private Order order;
    private Long createdAt, lastModified;

    public OrderEvent() {
    }

    public OrderEvent(OrderEventType type) {
        this.type = type;
    }

    public OrderEvent(OrderEventType type, Order order) {
        this.type = type;
        this.order = order;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long id) {
        eventId = id;
    }

    public OrderEventType getType() {
        return type;
    }

    public void setType(OrderEventType type) {
        this.type = type;
    }

    public Order getEntity() {
        return order;
    }

    public void setEntity(Order entity) {
        this.order = entity;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return "OrderEvent{"
                + "eventId=" + eventId
                + ", type=" + type
                + ", order=" + order
                + ", createdAt=" + createdAt
                + ", lastModified=" + lastModified
                + "} " + super.toString();
    }
}
