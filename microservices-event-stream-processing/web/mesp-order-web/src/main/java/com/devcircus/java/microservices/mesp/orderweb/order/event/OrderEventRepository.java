package com.devcircus.java.microservices.mesp.orderweb.order.event;

import demo.event.EventRepository;

public interface OrderEventRepository extends EventRepository<OrderEvent, Long> {
}
