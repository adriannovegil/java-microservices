package demo.inventory.config;

import demo.inventory.domain.Inventory;
import demo.inventory.domain.InventoryStatus;
import demo.inventory.event.InventoryEvent;
import demo.inventory.event.InventoryEventType;
import demo.inventory.function.*;
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
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableStateMachineFactory(name = "inventoryStateMachineFactory")
public class InventoryStateMachineConfig extends EnumStateMachineConfigurerAdapter<InventoryStatus, InventoryEventType> {

    final private Logger log = Logger.getLogger(InventoryStateMachineConfig.class);

    @Override
    public void configure(StateMachineStateConfigurer<InventoryStatus, InventoryEventType> states) {
        try {
            states.withStates()
                    .initial(InventoryStatus.INVENTORY_CREATED)
                    .states(EnumSet.allOf(InventoryStatus.class));
        } catch (Exception e) {
            throw new RuntimeException("State machine configuration failed", e);
        }
    }

    private InventoryEvent applyEvent(StateContext<InventoryStatus, InventoryEventType> context, InventoryFunction inventoryFunction) {
        InventoryEvent event = null;
        log.info(String.format("Replicate event: %s", context.getMessage().getPayload()));

        if (context.getMessageHeader("event") != null) {
            event = context.getMessageHeaders().get("event", InventoryEvent.class);
            log.info(String.format("State replication complete: %s", event.getType()));
            inventoryFunction.apply(event);
        }

        return event;
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<InventoryStatus, InventoryEventType> transitions) {
        try {
            // Describe state machine transitions for inventorys
            transitions.withExternal()
                    .source(InventoryStatus.INVENTORY_CREATED)
                    .target(InventoryStatus.INVENTORY_CREATED)
                    .event(InventoryEventType.INVENTORY_CREATED)
                    .action(inventoryCreated())
                    .and()
                    .withExternal()
                    .source(InventoryStatus.INVENTORY_CREATED)
                    .target(InventoryStatus.RESERVATION_CONNECTED)
                    .event(InventoryEventType.RESERVATION_CONNECTED)
                    .action(reservationConnected())
                    .and()
                    .withExternal()
                    .source(InventoryStatus.RESERVATION_CONNECTED)
                    .target(InventoryStatus.INVENTORY_RESERVED)
                    .event(InventoryEventType.INVENTORY_RESERVED)
                    .action(inventoryReserved())
                    .and()
                    .withExternal()
                    .source(InventoryStatus.INVENTORY_RESERVED)
                    .target(InventoryStatus.RESERVATION_PENDING)
                    .event(InventoryEventType.INVENTORY_RELEASED)
                    .action(inventoryReleased());
        } catch (Exception e) {
            throw new RuntimeException("Could not configure state machine transitions", e);
        }
    }

    @Bean
    public Action<InventoryStatus, InventoryEventType> inventoryCreated() {
        return context -> applyEvent(context,
                new InventoryCreated(context, event -> {
                    log.info(event.getType() + ": " + event.getLink("inventory").getHref());
                    // Get the inventory resource for the event
                    Traverson traverson = new Traverson(
                            URI.create(event.getLink("inventory").getHref()),
                            MediaTypes.HAL_JSON
                    );

                    Map<String, Object> template = new HashMap<>();
                    template.put("status", InventoryStatus.RESERVATION_PENDING);

                    return traverson.follow("self", "commands", "updateInventoryStatus")
                            .withTemplateParameters(template)
                            .toObject(Inventory.class);
                }));
    }

    @Bean
    public Action<InventoryStatus, InventoryEventType> reservationConnected() {
        return context -> applyEvent(context,
                new ReservationConnected(context, event -> {
                    log.info(event.getType() + ": " + event.getLink("inventory").getHref());
                    // Get the inventory resource for the event
                    Traverson traverson = new Traverson(
                            URI.create(event.getLink("inventory").getHref()),
                            MediaTypes.HAL_JSON
                    );

                    return traverson.follow("self")
                            .toEntity(Inventory.class)
                            .getBody();
                }));
    }

    @Bean
    public Action<InventoryStatus, InventoryEventType> inventoryReserved() {
        return context -> applyEvent(context,
                new InventoryReserved(context, event -> {
                    log.info(event.getType() + ": " + event.getLink("inventory").getHref());
                    // Get the inventory resource for the event
                    Traverson traverson = new Traverson(
                            URI.create(event.getLink("inventory").getHref()),
                            MediaTypes.HAL_JSON
                    );

                    return traverson.follow("self")
                            .toEntity(Inventory.class)
                            .getBody();
                }));
    }

    @Bean
    public Action<InventoryStatus, InventoryEventType> inventoryReleased() {
        return context -> applyEvent(context,
                new InventoryReleased(context, event -> {
                    log.info(event.getType() + ": " + event.getLink("inventory").getHref());
                    // Get the inventory resource for the event
                    Traverson traverson = new Traverson(
                            URI.create(event.getLink("inventory").getHref()),
                            MediaTypes.HAL_JSON
                    );

                    return traverson.follow("self")
                            .toEntity(Inventory.class)
                            .getBody();
                }));
    }
}
