package com.devcircus.java.microservices.mesp.warehouseweb.reservation.action;

import com.devcircus.java.microservices.mesp.warehouseweb.inventory.domain.Inventory;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.domain.InventoryService;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.domain.InventoryStatus;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.event.InventoryEvent;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.domain.Reservation;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.domain.ReservationModule;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.domain.ReservationStatus;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.event.ReservationEvent;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static com.devcircus.java.microservices.mesp.warehouseweb.inventory.event.InventoryEventType.INVENTORY_RELEASED;
import static com.devcircus.java.microservices.mesp.warehouseweb.reservation.event.ReservationEventType.RESERVATION_FAILED;

@Service
@Transactional
public class ReleaseInventory extends Action<Reservation> {

    private final Logger log = Logger.getLogger(this.getClass());
    private final InventoryService inventoryService;

    public ReleaseInventory(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public Reservation apply(Reservation reservation) {
        Assert.isTrue(reservation.getStatus() != ReservationStatus.RESERVATION_FAILED,
                "Reservation is already in a failed state");

        ReservationService reservationService = reservation.getModule(ReservationModule.class).getDefaultService();

        Inventory inventory = reservation.getInventory();

        try {
            // Remove the inventory and set the reservation to failed
            reservation.setInventory(null);
            reservation.setStatus(ReservationStatus.RESERVATION_FAILED);
            reservation = reservationService.update(reservation);

            // Trigger the reservation failed event
            reservation.sendAsyncEvent(new ReservationEvent(RESERVATION_FAILED, reservation));
        } catch (Exception ex) {
            log.error("Could not release the reservation's inventory", ex);
            if (reservation.getStatus() == ReservationStatus.RESERVATION_FAILED) {
                // Rollback the attempt
                reservation.setInventory(inventory);
                reservation.setStatus(ReservationStatus.RESERVATION_SUCCEEDED);
                reservation = reservationService.update(reservation);
            }
        } finally {
            if (inventory != null && reservation.getStatus() != ReservationStatus.RESERVATION_SUCCEEDED) {
                // Release the inventory
                inventory.setReservation(null);
                inventory.setStatus(InventoryStatus.RESERVATION_PENDING);
                inventory = inventoryService.update(inventory);

                // Trigger the inventory released event
                inventory.sendAsyncEvent(new InventoryEvent(INVENTORY_RELEASED, inventory));
            }
        }

        return reservation;
    }
}
