package com.devcircus.java.microservices.mesp.orderworker.order;

import com.devcircus.java.microservices.mesp.orderworker.order.domain.OrderStatus;
import com.devcircus.java.microservices.mesp.orderworker.order.event.OrderEventType;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StateService {

    private final StateMachineFactory<OrderStatus, OrderEventType> factory;

    public StateService(StateMachineFactory<OrderStatus, OrderEventType> factory) {
        this.factory = factory;
    }

    public StateMachine<OrderStatus, OrderEventType> newStateMachine() {
        // Create a new state machine in its initial state
        StateMachine<OrderStatus, OrderEventType> stateMachine
                = factory.getStateMachine(UUID.randomUUID().toString());

        // Start the new state machine
        stateMachine.start();

        return stateMachine;
    }
}
