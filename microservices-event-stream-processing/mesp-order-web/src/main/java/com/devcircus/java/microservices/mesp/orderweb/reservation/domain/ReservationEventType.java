package com.devcircus.java.microservices.mesp.orderweb.reservation.domain;

public enum ReservationEventType {
    RESERVATION_CREATED,
    INVENTORY_CONNECTED,
    ORDER_CONNECTED,
    RESERVATION_SUCCEEDED,
    RESERVATION_REQUESTED,
    RESERVATION_FAILED
}
