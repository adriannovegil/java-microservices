package demo.order;

import demo.order.domain.OrderStatus;
import demo.order.event.OrderEventType;
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
