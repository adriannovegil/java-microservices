package com.devcircus.java.microservices.mesp.warehouseworker.warehouse.event;

import com.devcircus.java.microservices.mesp.warehouseworker.warehouse.WarehouseStateFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Profile;

@EnableAutoConfiguration
@EnableBinding(WarehouseEventSink.class)
@Profile({"cloud", "development", "docker"})
public class WarehouseEventProcessor {

    private WarehouseStateFactory stateFactory;

    public WarehouseEventProcessor(WarehouseStateFactory stateFactory) {
        this.stateFactory = stateFactory;
    }

    @StreamListener(WarehouseEventSink.INPUT)
    public void streamListener(WarehouseEvent warehouseEvent) {
        stateFactory.apply(WarehouseEvent);
    }
}
