package com.devcircus.java.microservices.mesp.warehouseworker.reservation.event;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ReservationEventSink {

    String INPUT = "reservation";

    @Input(ReservationEventSink.INPUT)
    SubscribableChannel input();
}
