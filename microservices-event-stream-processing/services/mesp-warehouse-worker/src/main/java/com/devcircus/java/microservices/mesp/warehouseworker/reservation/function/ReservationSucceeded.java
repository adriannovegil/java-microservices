package com.devcircus.java.microservices.mesp.warehouseworker.reservation.function;

import com.devcircus.java.microservices.mesp.warehouseworker.reservation.domain.Reservation;
import com.devcircus.java.microservices.mesp.warehouseworker.reservation.domain.ReservationStatus;
import com.devcircus.java.microservices.mesp.warehouseworker.reservation.event.ReservationEvent;
import com.devcircus.java.microservices.mesp.warehouseworker.reservation.event.ReservationEventType;
import org.apache.log4j.Logger;
import org.springframework.statemachine.StateContext;

import java.util.function.Function;

public class ReservationSucceeded extends ReservationFunction {

    final private Logger log = Logger.getLogger(ReservationSucceeded.class);

    public ReservationSucceeded(StateContext<ReservationStatus, ReservationEventType> context, Function<ReservationEvent, Reservation> lambda) {
        super(context, lambda);
    }

    @Override
    public Reservation apply(ReservationEvent event) {
        log.info("Executing workflow for reservation succeeded...");
        return super.apply(event);
    }
}
