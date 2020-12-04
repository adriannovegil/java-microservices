package demo.function;

import demo.order.event.OrderEvent;
import demo.order.event.OrderEventType;
import demo.order.domain.Order;
import demo.order.domain.OrderStatus;
import org.apache.log4j.Logger;
import org.springframework.statemachine.StateContext;

import java.util.function.Function;

public class ReservationSucceeded extends OrderFunction {

    final private Logger log = Logger.getLogger(ReservationSucceeded.class);

    public ReservationSucceeded(StateContext<OrderStatus, OrderEventType> context, Function<OrderEvent, Order> lambda) {
        super(context, lambda);
    }

    @Override
    public Order apply(OrderEvent event) {
        log.info("Executing workflow for reservation succeeded...");
        return super.apply(event);
    }
}
