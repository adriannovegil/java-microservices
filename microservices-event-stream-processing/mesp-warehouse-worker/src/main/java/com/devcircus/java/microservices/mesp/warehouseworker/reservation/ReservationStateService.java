package com.devcircus.java.microservices.mesp.warehouseworker.reservation;

import com.devcircus.java.microservices.mesp.warehouseworker.reservation.domain.ReservationStatus;
import com.devcircus.java.microservices.mesp.warehouseworker.reservation.event.ReservationEventType;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReservationStateService {

    private final StateMachineFactory<ReservationStatus, ReservationEventType> factory;

    public ReservationStateService(StateMachineFactory<ReservationStatus, ReservationEventType> reservationStateMachineFactory) {
        this.factory = reservationStateMachineFactory;
    }

    public StateMachine<ReservationStatus, ReservationEventType> newStateMachine() {
        // Create a new state machine in its initial state
        StateMachine<ReservationStatus, ReservationEventType> stateMachine
                = factory.getStateMachine(UUID.randomUUID().toString());

        // Start the new state machine
        stateMachine.start();

        return stateMachine;
    }
}
