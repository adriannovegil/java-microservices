package com.devcircus.java.microservices.mesp.warehouseweb.inventory.domain;

import com.devcircus.java.microservices.mesp.warehouseweb.inventory.repository.InventoryRepository;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.domain.Reservation;
import org.apache.log4j.Logger;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

@org.springframework.stereotype.Service
public class InventoryService extends Service<Inventory, Long> {

    private final Logger log = Logger.getLogger(InventoryService.class);
    private final InventoryRepository inventoryRepository;
    private final RedissonClient redissonClient;

    public InventoryService(InventoryRepository inventoryRepository, RedissonClient redissonClient) {
        this.inventoryRepository = inventoryRepository;
        this.redissonClient = redissonClient;
    }

    public Inventory create(Inventory inventory) {

        // Save the inventory to the repository
        inventory = inventoryRepository.saveAndFlush(inventory);

        return inventory;
    }

    public Inventory get(Long id) {
        return inventoryRepository.findOne(id);
    }

    public Inventory update(Inventory inventory) {
        Assert.notNull(inventory.getIdentity(), "Inventory id must be present in the resource URL");
        Assert.notNull(inventory, "Inventory request body cannot be null");

        Assert.state(inventoryRepository.exists(inventory.getIdentity()),
                "The inventory with the supplied id does not exist");

        Inventory currentInventory = get(inventory.getIdentity());
        currentInventory.setStatus(inventory.getStatus());
        currentInventory.setProductId(inventory.getProductId());
        currentInventory.setReservation(inventory.getReservation());
        currentInventory.setWarehouse(inventory.getWarehouse());

        return inventoryRepository.saveAndFlush(currentInventory);
    }

    public boolean delete(Long id) {
        Assert.state(inventoryRepository.exists(id),
                "The inventory with the supplied id does not exist");
        this.inventoryRepository.delete(id);
        return true;
    }

    public Inventory findAvailableInventory(Reservation reservation) {
        Assert.notNull(reservation.getWarehouse(), "Reservation must be connected to a warehouse");
        Assert.notNull(reservation.getProductId(), "Reservation must contain a valid product identifier");

        Boolean reserved = false;
        Inventory inventory = null;

        while (!reserved) {
            inventory = inventoryRepository
                    .findFirstInventoryByWarehouseIdAndProductIdAndStatus(reservation.getWarehouse()
                            .getIdentity(), reservation.getProductId(), InventoryStatus.RESERVATION_PENDING);
            if (inventory != null) {
                // Acquire lock
                RLock inventoryLock = redissonClient
                        .getLock(String.format("inventory_%s", inventory.getIdentity().toString()));

                Boolean lock = false;

                try {
                    lock = inventoryLock.tryLock(1, 5000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    log.error("Interrupted while acquiring lock on inventory", e);
                }

                if (lock) {
                    inventory.setStatus(InventoryStatus.RESERVATION_CONNECTED);
                    inventory = update(inventory);

                    // Reserve the inventory
                    inventory = inventory.reserve(reservation.getIdentity());

                    inventoryLock.unlock();
                }

                reserved = lock;
            } else {
                reserved = true;
            }
        }

        return inventory;
    }
}
