package com.devcircus.java.microservices.mesp.warehouseweb.reservation.domain;

import com.devcircus.java.microservices.mesp.warehouseweb.reservation.event.ReservationEvent;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.event.ReservationEventService;

@org.springframework.stereotype.Service
public class ReservationModule extends Module<Reservation> {

    private final ReservationService reservationService;
    private final ReservationEventService eventService;

    public ReservationModule(ReservationService reservationService, ReservationEventService eventService) {
        this.reservationService = reservationService;
        this.eventService = eventService;
    }

    public ReservationService getReservationService() {
        return reservationService;
    }

    public EventService<ReservationEvent, Long> getEventService() {
        return eventService;
    }

    @Override
    public ReservationService getDefaultService() {
        return reservationService;
    }

    @Override
    public ReservationEventService getDefaultEventService() {
        return eventService;
    }
}
