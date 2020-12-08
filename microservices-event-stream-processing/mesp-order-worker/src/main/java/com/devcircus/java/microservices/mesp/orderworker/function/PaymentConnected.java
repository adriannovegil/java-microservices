package com.devcircus.java.microservices.mesp.orderworker.function;

import demo.order.event.OrderEvent;
import demo.order.event.OrderEventType;
import demo.order.domain.Order;
import demo.order.domain.OrderStatus;
import org.apache.log4j.Logger;
import org.springframework.statemachine.StateContext;

import java.util.function.Function;

public class PaymentConnected extends OrderFunction {

    final private Logger log = Logger.getLogger(PaymentConnected.class);

    public PaymentConnected(StateContext<OrderStatus, OrderEventType> context, Function<OrderEvent, Order> lambda) {
        super(context, lambda);
    }

    @Override
    public Order apply(OrderEvent event) {
        log.info("Executing workflow for payment connected...");
        return super.apply(event);
    }
}
