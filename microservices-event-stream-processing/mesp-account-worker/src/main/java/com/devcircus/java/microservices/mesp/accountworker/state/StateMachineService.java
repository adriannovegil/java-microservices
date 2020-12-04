package demo.state;

import demo.account.AccountStatus;
import demo.event.AccountEventType;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StateMachineService {

    private final StateMachineFactory<AccountStatus, AccountEventType> factory;

    public StateMachineService(StateMachineFactory<AccountStatus, AccountEventType> factory) {
        this.factory = factory;
    }

    public StateMachine<AccountStatus, AccountEventType> getStateMachine() {
        // Create a new state machine in its initial state
        StateMachine<AccountStatus, AccountEventType> stateMachine
                = factory.getStateMachine(UUID.randomUUID().toString());

        // Start the new state machine
        stateMachine.start();

        return stateMachine;
    }
}
