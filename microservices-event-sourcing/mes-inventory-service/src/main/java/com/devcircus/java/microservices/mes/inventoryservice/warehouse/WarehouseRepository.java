package com.devcircus.java.microservices.mes.inventoryservice.warehouse;

import org.springframework.data.neo4j.repository.GraphRepository;

public interface WarehouseRepository extends GraphRepository<Warehouse> {
}
