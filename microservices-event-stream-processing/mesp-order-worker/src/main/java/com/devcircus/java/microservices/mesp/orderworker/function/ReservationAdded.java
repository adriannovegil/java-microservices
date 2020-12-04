package demo.function;

import demo.order.domain.Order;
import demo.order.domain.OrderStatus;
import demo.order.event.OrderEvent;
import demo.order.event.OrderEventType;
import org.apache.log4j.Logger;
import org.springframework.statemachine.StateContext;

import java.util.function.Function;

public class ReservationAdded extends OrderFunction {

    final private Logger log = Logger.getLogger(ReservationAdded.class);

    public ReservationAdded(StateContext<OrderStatus, OrderEventType> context, Function<OrderEvent, Order> lambda) {
        super(context, lambda);
    }

    @Override
    public Order apply(OrderEvent event) {
        log.info("Executing workflow for reservation added...");
        return super.apply(event);
    }
}
