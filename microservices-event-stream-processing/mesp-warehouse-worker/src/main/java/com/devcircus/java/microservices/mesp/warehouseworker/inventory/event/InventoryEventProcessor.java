package com.devcircus.java.microservices.mesp.warehouseworker.inventory.event;

import com.devcircus.java.microservices.mesp.warehouseworker.inventory.InventoryStateFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Profile;

@EnableAutoConfiguration
@EnableBinding(InventoryEventSink.class)
@Profile({"cloud", "development", "docker"})
public class InventoryEventProcessor {

    private InventoryStateFactory stateFactory;

    public InventoryEventProcessor(InventoryStateFactory stateFactory) {
        this.stateFactory = stateFactory;
    }

    @StreamListener(InventoryEventSink.INPUT)
    public void streamListener(InventoryEvent inventoryEvent) {
        stateFactory.apply(inventoryEvent);
    }
}
