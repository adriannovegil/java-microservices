package com.devcircus.java.microservices.mesp.orderweb.reservation.domain;

import demo.event.Event;

public class ReservationEvent extends Event<Reservation, ReservationEventType, Long> {

    private Long eventId;
    private ReservationEventType type;
    private Reservation reservation;
    private Long createdAt;
    private Long lastModified;

    public ReservationEvent() {
    }

    public ReservationEvent(ReservationEventType type) {
        this.type = type;
    }

    public ReservationEvent(ReservationEventType type, Reservation reservation) {
        this.type = type;
        this.reservation = reservation;
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
    public ReservationEventType getType() {
        return type;
    }

    @Override
    public void setType(ReservationEventType type) {
        this.type = type;
    }

    @Override
    public Reservation getEntity() {
        return reservation;
    }

    @Override
    public void setEntity(Reservation entity) {
        this.reservation = entity;
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
