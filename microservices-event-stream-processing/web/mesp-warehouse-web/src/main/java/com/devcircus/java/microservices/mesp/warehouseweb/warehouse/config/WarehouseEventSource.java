package com.devcircus.java.microservices.mesp.warehouseweb.warehouse.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface WarehouseEventSource {

    String OUTPUT = "warehouse";

    @Output(WarehouseEventSource.OUTPUT)
    MessageChannel output();
}
