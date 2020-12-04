package demo.inventory.function;

import demo.inventory.domain.Inventory;
import demo.inventory.domain.InventoryStatus;
import demo.inventory.event.InventoryEvent;
import demo.inventory.event.InventoryEventType;
import org.apache.log4j.Logger;
import org.springframework.statemachine.StateContext;

import java.util.function.Function;

public abstract class InventoryFunction {

    final private Logger log = Logger.getLogger(InventoryFunction.class);
    final protected StateContext<InventoryStatus, InventoryEventType> context;
    final protected Function<InventoryEvent, Inventory> lambda;

    public InventoryFunction(StateContext<InventoryStatus, InventoryEventType> context,
            Function<InventoryEvent, Inventory> lambda) {
        this.context = context;
        this.lambda = lambda;
    }

    public Inventory apply(InventoryEvent event) {
        // Execute the lambda function
        Inventory result = lambda.apply(event);
        context.getExtendedState().getVariables().put("inventory", result);
        log.info("Inventory function: " + event.getType());
        return result;
    }
}
