package demo.inventory;

import demo.inventory.domain.Inventory;
import demo.inventory.domain.InventoryStatus;
import demo.inventory.event.InventoryEvent;
import demo.inventory.event.InventoryEventType;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InventoryStateService {

    private final StateMachineFactory<InventoryStatus, InventoryEventType> factory;

    public InventoryStateService(StateMachineFactory<InventoryStatus, InventoryEventType> inventoryStateMachineFactory) {
        this.factory = inventoryStateMachineFactory;
    }

    public StateMachine<InventoryStatus, InventoryEventType> newStateMachine() {
        // Create a new state machine in its initial state
        StateMachine<InventoryStatus, InventoryEventType> stateMachine
                = factory.getStateMachine(UUID.randomUUID().toString());

        // Start the new state machine
        stateMachine.start();

        return stateMachine;
    }
}
