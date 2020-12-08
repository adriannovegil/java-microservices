package com.devcircus.java.microservices.mesp.warehouseweb.inventory.repository;

import com.devcircus.java.microservices.mesp.warehouseweb.inventory.domain.Inventory;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.domain.InventoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Inventory findFirstInventoryByWarehouseIdAndProductIdAndStatus(@Param("warehouseId") Long warehouseId,
            @Param("productId") String productId, @Param("status") InventoryStatus status);
}
