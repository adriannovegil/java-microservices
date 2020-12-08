package com.devcircus.java.microservices.mesp.orderweb.warehouse.domain;

public class WarehouseEvent extends Event<Warehouse, WarehouseEventType, Long> {

    private Long eventId;
    private WarehouseEventType type;
    private Warehouse entity;
    private Long createdAt;
    private Long lastModified;

    public WarehouseEvent() {
    }

    public WarehouseEvent(WarehouseEventType type) {
        this.type = type;
    }

    public WarehouseEvent(WarehouseEventType type, Warehouse entity) {
        this.type = type;
        this.entity = entity;
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
    public WarehouseEventType getType() {
        return type;
    }

    @Override
    public void setType(WarehouseEventType type) {
        this.type = type;
    }

    @Override
    public Warehouse getEntity() {
        return entity;
    }

    @Override
    public void setEntity(Warehouse entity) {
        this.entity = entity;
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

    @Override
    public String toString() {
        return "WarehouseEvent{"
                + "eventId=" + eventId
                + ", type=" + type
                + ", entity=" + entity
                + ", createdAt=" + createdAt
                + ", lastModified=" + lastModified
                + "} " + super.toString();
    }
}
