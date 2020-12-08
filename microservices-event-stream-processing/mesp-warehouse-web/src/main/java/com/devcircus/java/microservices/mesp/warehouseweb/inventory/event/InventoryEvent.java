package com.devcircus.java.microservices.mesp.warehouseweb.inventory.event;

import com.devcircus.java.microservices.mesp.warehouseweb.inventory.controller.InventoryController;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.domain.Inventory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.hateoas.Link;

import javax.persistence.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = {
    @Index(name = "IDX_INVENTORY_EVENT", columnList = "entity_id")})
public class InventoryEvent extends Event<Inventory, InventoryEventType, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long eventId;

    @Enumerated(EnumType.STRING)
    private InventoryEventType type;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JsonIgnore
    private Inventory entity;

    @CreatedDate
    private Long createdAt;

    @LastModifiedDate
    private Long lastModified;

    public InventoryEvent() {
    }

    public InventoryEvent(InventoryEventType type) {
        this.type = type;
    }

    public InventoryEvent(InventoryEventType type, Inventory entity) {
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
    public InventoryEventType getType() {
        return type;
    }

    @Override
    public void setType(InventoryEventType type) {
        this.type = type;
    }

    @Override
    public Inventory getEntity() {
        return entity;
    }

    @Override
    public void setEntity(Inventory entity) {
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
    public Link getId() {
        return linkTo(InventoryController.class).slash("inventory").slash(getEntity().getIdentity()).slash("events")
                .slash(getEventId()).withSelfRel();
    }

    @Override
    public String toString() {
        return "InventoryEvent{"
                + "eventId=" + eventId
                + ", type=" + type
                + ", entity=" + entity
                + ", createdAt=" + createdAt
                + ", lastModified=" + lastModified
                + "} " + super.toString();
    }
}
