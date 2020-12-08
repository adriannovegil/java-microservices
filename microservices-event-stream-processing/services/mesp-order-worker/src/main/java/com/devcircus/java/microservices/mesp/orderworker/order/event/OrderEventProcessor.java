package com.devcircus.java.microservices.mesp.orderworker.order.event;

import com.devcircus.java.microservices.mesp.orderworker.order.StateFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Profile;

@EnableAutoConfiguration
@EnableBinding(Sink.class)
@Profile({"cloud", "development", "docker"})
public class OrderEventProcessor {

    private StateFactory stateFactory;

    public OrderEventProcessor(StateFactory stateFactory) {
        this.stateFactory = stateFactory;
    }

    @StreamListener(Sink.INPUT)
    public void streamListener(OrderEvent orderEvent) {
        stateFactory.apply(orderEvent);
    }
}
