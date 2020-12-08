package com.devcircus.java.microservices.mesp.warehouseworker.inventory.function;

import com.devcircus.java.microservices.mesp.warehouseworker.inventory.domain.Inventory;
import com.devcircus.java.microservices.mesp.warehouseworker.inventory.domain.InventoryStatus;
import com.devcircus.java.microservices.mesp.warehouseworker.inventory.event.InventoryEvent;
import com.devcircus.java.microservices.mesp.warehouseworker.inventory.event.InventoryEventType;
import org.apache.log4j.Logger;
import org.springframework.statemachine.StateContext;

import java.util.function.Function;

public class InventoryCreated extends InventoryFunction {

    final private Logger log = Logger.getLogger(InventoryCreated.class);

    public InventoryCreated(StateContext<InventoryStatus, InventoryEventType> context, Function<InventoryEvent, Inventory> lambda) {
        super(context, lambda);
    }

    @Override
    public Inventory apply(InventoryEvent event) {
        log.info("Executing workflow for inventory created...");
        return super.apply(event);
    }
}
