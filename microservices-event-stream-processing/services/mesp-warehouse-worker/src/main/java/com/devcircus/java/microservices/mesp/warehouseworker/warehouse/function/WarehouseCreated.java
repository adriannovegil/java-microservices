package com.devcircus.java.microservices.mesp.warehouseworker.warehouse.function;

import com.devcircus.java.microservices.mesp.warehouseworker.warehouse.domain.Warehouse;
import com.devcircus.java.microservices.mesp.warehouseworker.warehouse.domain.WarehouseStatus;
import com.devcircus.java.microservices.mesp.warehouseworker.warehouse.event.WarehouseEvent;
import com.devcircus.java.microservices.mesp.warehouseworker.warehouse.event.WarehouseEventType;
import org.apache.log4j.Logger;
import org.springframework.statemachine.StateContext;

import java.util.function.Function;

public class WarehouseCreated extends WarehouseFunction {

    final private Logger log = Logger.getLogger(WarehouseCreated.class);

    public WarehouseCreated(StateContext<WarehouseStatus, WarehouseEventType> context, Function<WarehouseEvent, Warehouse> lambda) {
        super(context, lambda);
    }

    @Override
    public Warehouse apply(WarehouseEvent event) {
        log.info("Executing workflow for warehouse created...");
        return super.apply(event);
    }
}
