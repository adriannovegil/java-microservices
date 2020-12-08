package com.devcircus.java.microservices.mesp.springbootstarterawslambda.state;

import com.devcircus.java.microservices.mesp.springbootstarterawslambda.event.PaymentEventType;
import com.devcircus.java.microservices.mesp.springbootstarterawslambda.payment.PaymentStatus;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StateMachineService {

    private final StateMachineFactory<PaymentStatus, PaymentEventType> factory;

    public StateMachineService(StateMachineFactory<PaymentStatus, PaymentEventType> factory) {
        this.factory = factory;
    }

    public StateMachine<PaymentStatus, PaymentEventType> getStateMachine() {
        // Create a new state machine in its initial state
        StateMachine<PaymentStatus, PaymentEventType> stateMachine
                = factory.getStateMachine(UUID.randomUUID().toString());

        // Start the new state machine
        stateMachine.start();

        return stateMachine;
    }
}
