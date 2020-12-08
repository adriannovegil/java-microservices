package com.devcircus.java.microservices.mesp.warehouseweb.config;

import com.devcircus.java.microservices.mesp.warehouseweb.inventory.config.InventoryEventSource;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.event.InventoryEventRepository;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.event.InventoryEventService;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.config.ReservationEventSource;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.event.ReservationEventRepository;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.event.ReservationEventService;
import com.devcircus.java.microservices.mesp.warehouseweb.warehouse.config.WarehouseEventSource;
import com.devcircus.java.microservices.mesp.warehouseweb.warehouse.event.WarehouseEventRepository;
import com.devcircus.java.microservices.mesp.warehouseweb.warehouse.event.WarehouseEventService;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class EventConfig {

    @Bean
    public EventSource inventoryChannel(InventoryEventSource eventSource) {
        return new EventSource(eventSource.output());
    }

    @Bean
    public EventSource warehouseChannel(WarehouseEventSource eventSource) {
        return new EventSource(eventSource.output());
    }

    @Bean
    public EventSource reservationChannel(ReservationEventSource eventSource) {
        return new EventSource(eventSource.output());
    }

    @Bean
    public InventoryEventService inventoryEventService(RestTemplate restTemplate, InventoryEventRepository inventoryEventRepository, InventoryEventSource eventStream, Source source) {
        return new InventoryEventService(inventoryEventRepository, inventoryChannel(eventStream), restTemplate, source);
    }

    @Bean
    public WarehouseEventService warehouseEventService(RestTemplate restTemplate, WarehouseEventRepository warehouseEventRepository, WarehouseEventSource eventStream, Source source) {
        return new WarehouseEventService(warehouseEventRepository, warehouseChannel(eventStream), restTemplate, source);
    }

    @Bean
    public ReservationEventService reservationEventService(RestTemplate restTemplate, ReservationEventRepository reservationEventRepository, ReservationEventSource eventStream, Source source) {
        return new ReservationEventService(reservationEventRepository, reservationChannel(eventStream), restTemplate,
                source);
    }

}
