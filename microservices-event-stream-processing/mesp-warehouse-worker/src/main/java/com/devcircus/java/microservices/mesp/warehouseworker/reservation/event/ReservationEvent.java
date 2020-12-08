package com.devcircus.java.microservices.mesp.warehouseworker.reservation.event;

import com.devcircus.java.microservices.mesp.warehouseworker.domain.AbstractEntity;

public class ReservationEvent extends AbstractEntity {

    private ReservationEventType type;

    public ReservationEvent() {
    }

    public ReservationEvent(ReservationEventType type) {
        this.type = type;
    }

    public ReservationEventType getType() {
        return type;
    }

    public void setType(ReservationEventType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ReservationEvent{"
                + "type=" + type
                + "} " + super.toString();
    }
}
