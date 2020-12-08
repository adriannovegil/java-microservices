package com.devcircus.java.microservices.mesp.warehouseworker.warehouse.config;

import com.devcircus.java.microservices.mesp.warehouseworker.warehouse.domain.Warehouse;
import com.devcircus.java.microservices.mesp.warehouseworker.warehouse.domain.WarehouseStatus;
import com.devcircus.java.microservices.mesp.warehouseworker.warehouse.event.WarehouseEvent;
import com.devcircus.java.microservices.mesp.warehouseworker.warehouse.event.WarehouseEventType;
import com.devcircus.java.microservices.mesp.warehouseworker.warehouse.function.WarehouseFunction;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.net.URI;
import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory(name = "warehouseStateMachineFactory")
public class WarehouseStateMachineConfig extends EnumStateMachineConfigurerAdapter<WarehouseStatus, WarehouseEventType> {

    final private Logger log = Logger.getLogger(WarehouseStateMachineConfig.class);

    @Override
    public void configure(StateMachineStateConfigurer<WarehouseStatus, WarehouseEventType> states) {
        try {
            states.withStates()
                    .initial(WarehouseStatus.WAREHOUSE_CREATED)
                    .states(EnumSet.allOf(WarehouseStatus.class));
        } catch (Exception e) {
            throw new RuntimeException("State machine configuration failed", e);
        }
    }

    private WarehouseEvent applyEvent(StateContext<WarehouseStatus, WarehouseEventType> context, WarehouseFunction warehouseFunction) {
        WarehouseEvent event = null;
        log.info(String.format("Replicate event: %s", context.getMessage().getPayload()));

        if (context.getMessageHeader("event") != null) {
            event = context.getMessageHeaders().get("event", WarehouseEvent.class);
            log.info(String.format("State replication complete: %s", event.getType()));
            warehouseFunction.apply(event);
        }

        return event;
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<WarehouseStatus, WarehouseEventType> transitions) {
        try {
            // Describe state machine transitions for warehouses
            transitions.withExternal()
                    .source(WarehouseStatus.WAREHOUSE_CREATED)
                    .target(WarehouseStatus.WAREHOUSE_CREATED)
                    .event(WarehouseEventType.WAREHOUSE_CREATED)
                    .action(warehouseCreated());
        } catch (Exception e) {
            throw new RuntimeException("Could not configure state machine transitions", e);
        }
    }

    @Bean
    public Action<WarehouseStatus, WarehouseEventType> warehouseCreated() {
        return context -> applyEvent(context,
                new WarehouseCreated(context, event -> {
                    log.info(event.getType() + ": " + event.getLink("warehouse").getHref());
                    // Get the warehouse resource for the event
                    Traverson traverson = new Traverson(
                            URI.create(event.getLink("warehouse").getHref()),
                            MediaTypes.HAL_JSON
                    );

                    return traverson.follow("self")
                            .toEntity(Warehouse.class)
                            .getBody();
                }));
    }
}
