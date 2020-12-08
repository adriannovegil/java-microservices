package com.devcircus.java.microservices.mesp.orderworker.reservation.domain;

public enum ReservationStatus {
    RESERVATION_CREATED,
    RESERVATION_PENDING,
    INVENTORY_CONNECTED,
    ORDER_CONNECTED,
    RESERVATION_SUCCEEDED,
    RESERVATION_FAILED
}
