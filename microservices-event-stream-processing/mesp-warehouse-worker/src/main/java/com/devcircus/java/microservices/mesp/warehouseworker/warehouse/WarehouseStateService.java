package com.devcircus.java.microservices.mesp.warehouseworker.warehouse;

import com.devcircus.java.microservices.mesp.warehouseworker.warehouse.domain.WarehouseStatus;
import com.devcircus.java.microservices.mesp.warehouseworker.warehouse.event.WarehouseEventType;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WarehouseStateService {

    private final StateMachineFactory<WarehouseStatus, WarehouseEventType> factory;

    public WarehouseStateService(StateMachineFactory<WarehouseStatus, WarehouseEventType> warehouseStateMachineFactory) {
        this.factory = warehouseStateMachineFactory;
    }

    public StateMachine<WarehouseStatus, WarehouseEventType> newStateMachine() {
        // Create a new state machine in its initial state
        StateMachine<WarehouseStatus, WarehouseEventType> stateMachine
                = factory.getStateMachine(UUID.randomUUID().toString());

        // Start the new state machine
        stateMachine.start();

        return stateMachine;
    }
}
