package com.devcircus.java.microservices.mesp.warehouseweb.inventory.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(InventoryEventSource.class)
public class InventoryStreamConfig {
}
