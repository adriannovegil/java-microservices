package com.devcircus.java.microservices.mesp.orderworker.function;

import demo.order.event.OrderEvent;
import demo.order.event.OrderEventType;
import demo.order.domain.Order;
import demo.order.domain.OrderStatus;
import org.apache.log4j.Logger;
import org.springframework.statemachine.StateContext;

import java.util.function.Function;

public class PaymentCreated extends OrderFunction {

    final private Logger log = Logger.getLogger(PaymentCreated.class);

    public PaymentCreated(StateContext<OrderStatus, OrderEventType> context, Function<OrderEvent, Order> lambda) {
        super(context, lambda);
    }

    @Override
    public Order apply(OrderEvent event) {
        log.info("Executing workflow for payment created...");
        return super.apply(event);
    }
}
