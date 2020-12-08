package com.devcircus.java.microservices.mesp.warehouseworker.reservation.function;

import com.devcircus.java.microservices.mesp.warehouseworker.reservation.domain.Reservation;
import com.devcircus.java.microservices.mesp.warehouseworker.reservation.domain.ReservationStatus;
import com.devcircus.java.microservices.mesp.warehouseworker.reservation.event.ReservationEvent;
import com.devcircus.java.microservices.mesp.warehouseworker.reservation.event.ReservationEventType;
import org.apache.log4j.Logger;
import org.springframework.statemachine.StateContext;

import java.util.function.Function;

public class ReservationCreated extends ReservationFunction {

    final private Logger log = Logger.getLogger(ReservationCreated.class);

    public ReservationCreated(StateContext<ReservationStatus, ReservationEventType> context, Function<ReservationEvent, Reservation> lambda) {
        super(context, lambda);
    }

    @Override
    public Reservation apply(ReservationEvent event) {
        log.info("Executing workflow for reservation created...");
        return super.apply(event);
    }
}
