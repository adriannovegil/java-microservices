package com.devcircus.java.microservices.mesp.orderworker.function;

import demo.order.domain.Order;
import demo.order.domain.OrderStatus;
import demo.order.event.OrderEvent;
import demo.order.event.OrderEventType;
import org.apache.log4j.Logger;
import org.springframework.statemachine.StateContext;

import java.util.function.Function;

public abstract class OrderFunction {

    final private Logger log = Logger.getLogger(OrderFunction.class);
    final protected StateContext<OrderStatus, OrderEventType> context;
    final protected Function<OrderEvent, Order> lambda;

    public OrderFunction(StateContext<OrderStatus, OrderEventType> context,
            Function<OrderEvent, Order> lambda) {
        this.context = context;
        this.lambda = lambda;
    }

    public Order apply(OrderEvent event) {
        // Execute the lambda function
        Order result = lambda.apply(event);
        context.getExtendedState().getVariables().put("order", result);
        log.info("Order function: " + event.getType());
        return result;
    }
}
