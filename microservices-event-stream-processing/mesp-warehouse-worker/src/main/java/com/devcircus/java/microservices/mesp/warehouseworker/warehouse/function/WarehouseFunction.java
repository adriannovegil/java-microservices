package com.devcircus.java.microservices.mesp.warehouseworker.warehouse.function;

import com.devcircus.java.microservices.mesp.warehouseworker.warehouse.domain.Warehouse;
import com.devcircus.java.microservices.mesp.warehouseworker.warehouse.domain.WarehouseStatus;
import com.devcircus.java.microservices.mesp.warehouseworker.warehouse.event.WarehouseEvent;
import com.devcircus.java.microservices.mesp.warehouseworker.warehouse.event.WarehouseEventType;
import org.apache.log4j.Logger;
import org.springframework.statemachine.StateContext;

import java.util.function.Function;

public abstract class WarehouseFunction {

    final private Logger log = Logger.getLogger(WarehouseFunction.class);
    final protected StateContext<WarehouseStatus, WarehouseEventType> context;
    final protected Function<WarehouseEvent, Warehouse> lambda;

    public WarehouseFunction(StateContext<WarehouseStatus, WarehouseEventType> context,
            Function<WarehouseEvent, Warehouse> lambda) {
        this.context = context;
        this.lambda = lambda;
    }

    public Warehouse apply(WarehouseEvent event) {
        // Execute the lambda function
        Warehouse result = lambda.apply(event);
        context.getExtendedState().getVariables().put("warehouse", result);
        log.info("Warehouse function: " + event.getType());
        return result;
    }
}
