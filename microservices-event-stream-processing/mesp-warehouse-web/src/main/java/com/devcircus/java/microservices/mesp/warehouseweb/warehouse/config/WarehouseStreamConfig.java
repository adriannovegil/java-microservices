package com.devcircus.java.microservices.mesp.warehouseweb.warehouse.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(WarehouseEventSource.class)
public class WarehouseStreamConfig {
}
