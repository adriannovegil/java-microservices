package com.devcircus.java.microservices.mesp.orderworker.function;

import com.devcircus.java.microservices.mesp.orderworker.order.domain.Order;
import com.devcircus.java.microservices.mesp.orderworker.order.domain.OrderStatus;
import com.devcircus.java.microservices.mesp.orderworker.order.event.OrderEvent;
import com.devcircus.java.microservices.mesp.orderworker.order.event.OrderEventType;
import org.apache.log4j.Logger;
import org.springframework.statemachine.StateContext;

import java.util.function.Function;

public class AccountConnected extends OrderFunction {

    final private Logger log = Logger.getLogger(AccountConnected.class);

    public AccountConnected(StateContext<OrderStatus, OrderEventType> context, Function<OrderEvent, Order> lambda) {
        super(context, lambda);
    }

    @Override
    public Order apply(OrderEvent event) {
        log.info("Executing workflow for account connected...");
        return super.apply(event);
    }
}
