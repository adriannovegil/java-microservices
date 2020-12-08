package com.devcircus.java.microservices.mesp.orderweb.reservation.domain;

public enum ReservationStatus {
    RESERVATION_CREATED,
    INVENTORY_CONNECTED,
    ORDER_CONNECTED,
    RESERVATION_SUCCEEDED,
    RESERVATION_PENDING, RESERVATION_FAILED
}
