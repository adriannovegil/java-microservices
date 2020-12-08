package com.devcircus.java.microservices.mesp.warehouseweb.inventory.action;

import com.devcircus.java.microservices.mesp.warehouseweb.inventory.domain.Inventory;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.domain.InventoryService;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.domain.InventoryStatus;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.event.InventoryEvent;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.event.InventoryEventType;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.domain.Reservation;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.domain.ReservationService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static demo.inventory.domain.InventoryStatus.RESERVATION_CONNECTED;

@Service
public class ReserveInventory extends Action<Inventory> {

    private final Logger log = Logger.getLogger(this.getClass());

    private final ReservationService reservationService;
    private final InventoryService inventoryService;

    public ReserveInventory(ReservationService reservationService, InventoryService inventoryService) {
        this.reservationService = reservationService;
        this.inventoryService = inventoryService;
    }

    public Inventory apply(Inventory inventory, Long reservationId) {
        Assert.isTrue(inventory.getStatus() == InventoryStatus.RESERVATION_CONNECTED,
                "Inventory must be in a reservation connected state");
        Assert.isTrue(inventory.getReservation() == null,
                "There is already a reservation attached to the inventory");

        Reservation reservation = reservationService.get(reservationId);
        Assert.notNull(reservation, "Reserve inventory failed, the reservation does not exist");

        try {
            // Trigger the reservation connected event
            inventory.sendAsyncEvent(new InventoryEvent(InventoryEventType.RESERVATION_CONNECTED, inventory));
        } catch (Exception ex) {
            log.error("Could not connect reservation to inventory", ex);
            inventory.setReservation(null);
            inventory.setStatus(InventoryStatus.RESERVATION_PENDING);
            inventory = inventoryService.update(inventory);
        } finally {
            if (inventory.getStatus() == RESERVATION_CONNECTED && inventory.getReservation() != null) {
                inventory.setStatus(InventoryStatus.INVENTORY_RESERVED);
                inventory = inventoryService.update(inventory);
                inventory.sendAsyncEvent(new InventoryEvent(InventoryEventType.INVENTORY_RESERVED, inventory));
            }
        }

        return inventory;
    }
}
