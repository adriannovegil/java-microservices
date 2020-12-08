package com.devcircus.java.microservices.mesp.warehouseweb.inventory.action;

import com.devcircus.java.microservices.mesp.warehouseweb.inventory.domain.Inventory;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.domain.InventoryService;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.domain.InventoryStatus;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.domain.ReservationService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateInventoryStatus extends Action<Inventory> {

    private final Logger log = Logger.getLogger(this.getClass());

    private final ReservationService reservationService;
    private final InventoryService inventoryService;

    public UpdateInventoryStatus(ReservationService reservationService, InventoryService inventoryService) {
        this.reservationService = reservationService;
        this.inventoryService = inventoryService;
    }

    public Inventory apply(Inventory inventory, InventoryStatus inventoryStatus) {
        // Save rollback status
        InventoryStatus rollbackStatus = inventory.getStatus();

        try {
            // Update status
            inventory.setStatus(inventoryStatus);
            inventory = inventoryService.update(inventory);
        } catch (Exception ex) {
            log.error("Could not update the status", ex);
            inventory.setStatus(rollbackStatus);
            inventory = inventoryService.update(inventory);
        }

        return inventory;
    }
}
