package com.devcircus.java.microservices.mesp.warehouseweb.reservation.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ReservationEventSource {

    String OUTPUT = "reservation";

    @Output(ReservationEventSource.OUTPUT)
    MessageChannel output();
}
