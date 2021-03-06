package com.devcircus.java.microservices.mesp.warehouseweb.inventory.controller;

import com.devcircus.java.microservices.mesp.warehouseweb.inventory.domain.Inventory;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.domain.InventoryService;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.domain.InventoryStatus;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.event.InventoryEvent;
import com.devcircus.java.microservices.mesp.warehouseweb.warehouse.controller.WarehouseController;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.hateoas.LinkBuilder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/v1")
public class InventoryController {

    private final InventoryService inventoryService;
    private final EventService<InventoryEvent, Long> eventService;
    private final DiscoveryClient discoveryClient;

    public InventoryController(InventoryService inventoryService, EventService<InventoryEvent, Long> eventService,
            DiscoveryClient discoveryClient) {
        this.inventoryService = inventoryService;
        this.eventService = eventService;
        this.discoveryClient = discoveryClient;
    }

    @PostMapping(path = "/inventory")
    public ResponseEntity createInventory(@RequestBody Inventory inventory) {
        return Optional.ofNullable(createInventoryResource(inventory))
                .map(e -> new ResponseEntity<>(e, HttpStatus.CREATED))
                .orElseThrow(() -> new RuntimeException("Inventory creation failed"));
    }

    @PutMapping(path = "/inventory/{id}")
    public ResponseEntity updateInventory(@RequestBody Inventory inventory, @PathVariable Long id) {
        return Optional.ofNullable(updateInventoryResource(id, inventory))
                .map(e -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Inventory update failed"));
    }

    @RequestMapping(path = "/inventory/{id}")
    public ResponseEntity getInventory(@PathVariable Long id) {
        return Optional.ofNullable(getInventoryResource(inventoryService.get(id)))
                .map(e -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/inventory/{id}")
    public ResponseEntity deleteInventory(@PathVariable Long id) {
        return Optional.ofNullable(inventoryService.delete(id))
                .map(e -> new ResponseEntity<>(HttpStatus.NO_CONTENT))
                .orElseThrow(() -> new RuntimeException("Inventory deletion failed"));
    }

    @RequestMapping(path = "/inventory/{id}/events")
    public ResponseEntity getInventoryEvents(@PathVariable Long id) {
        return Optional.of(getInventoryEventResources(id))
                .map(e -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Could not get inventory events"));
    }

    @RequestMapping(path = "/inventory/{id}/events/{eventId}")
    public ResponseEntity getInventoryEvent(@PathVariable Long id, @PathVariable Long eventId) {
        return Optional.of(getEventResource(eventId))
                .map(e -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Could not get order events"));
    }

    @PostMapping(path = "/inventory/{id}/events")
    public ResponseEntity appendInventoryEvent(@PathVariable Long id, @RequestBody InventoryEvent event) {
        return Optional.ofNullable(appendEventResource(id, event))
                .map(e -> new ResponseEntity<>(e, HttpStatus.CREATED))
                .orElseThrow(() -> new RuntimeException("Append inventory event failed"));
    }

    @RequestMapping(path = "/inventory/{id}/commands")
    public ResponseEntity getCommands(@PathVariable Long id) {
        return Optional.ofNullable(getCommandsResources(id))
                .map(e -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("The inventory could not be found"));
    }

    @RequestMapping(path = "/inventory/{id}/commands/reserve")
    public ResponseEntity reserve(@PathVariable Long id, @RequestParam(value = "reservationId") Long reservationId) {
        return Optional.ofNullable(inventoryService.get(id)
                .reserve(reservationId))
                .map(e -> new ResponseEntity<>(getInventoryResource(e), HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("The command could not be applied"));
    }

    @RequestMapping(path = "/inventory/{id}/commands/updateInventoryStatus")
    public ResponseEntity updateInventoryStatus(@PathVariable Long id, @RequestParam(value = "status") InventoryStatus status) {
        return Optional.ofNullable(inventoryService.get(id).updateStatus(status))
                .map(e -> new ResponseEntity<>(getInventoryResource(e), HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("The command could not be applied"));
    }

    private Resource<Inventory> createInventoryResource(Inventory inventory) {
        Assert.notNull(inventory, "Inventory body must not be null");

        // Create the new inventory
        inventory = inventoryService.create(inventory);

        return getInventoryResource(inventory);
    }

    private Resource<Inventory> updateInventoryResource(Long id, Inventory inventory) {
        inventory.setIdentity(id);
        return getInventoryResource(inventoryService.update(inventory));
    }

    private Resource<InventoryEvent> appendEventResource(Long inventoryId, InventoryEvent event) {
        Resource<InventoryEvent> eventResource = null;

        inventoryService.get(inventoryId)
                .sendAsyncEvent(event);

        if (event != null) {
            eventResource = new Resource<>(event,
                    linkTo(InventoryController.class)
                            .slash("inventory")
                            .slash(inventoryId)
                            .slash("events")
                            .slash(event.getEventId())
                            .withSelfRel(),
                    linkTo(InventoryController.class)
                            .slash("inventory")
                            .slash(inventoryId)
                            .withRel("inventory")
            );
        }

        return eventResource;
    }

    private InventoryEvent getEventResource(Long eventId) {
        return eventService.findOne(eventId);
    }

    private Events getInventoryEventResources(Long id) {
        return eventService.find(id);
    }

    private LinkBuilder linkBuilder(String name, Long id) {
        Method method;

        try {
            method = InventoryController.class.getMethod(name, Long.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return linkTo(InventoryController.class, method, id);
    }

    private Resource<Inventory> getInventoryResource(Inventory inventory) {
        if (inventory == null) {
            return null;
        }

        if (!inventory.hasLink("commands")) {
            // Add command link
            inventory.add(linkBuilder("getCommands", inventory.getIdentity()).withRel("commands"));
        }

        if (!inventory.hasLink("events")) {
            // Add get events link
            inventory.add(linkBuilder("getInventoryEvents", inventory.getIdentity()).withRel("events"));
        }

        if (inventory.getReservation() != null && !inventory.hasLink("reservation")) {
            // Add get reservation link
            inventory.add(linkTo(ReservationController.class)
                    .slash("reservations")
                    .slash(inventory.getReservation().getIdentity())
                    .withRel("reservation"));
        }

        if (inventory.getWarehouse() != null && !inventory.hasLink("warehouse")) {
            // Add get reservation link
            inventory.add(linkTo(WarehouseController.class)
                    .slash("warehouses")
                    .slash(inventory.getWarehouse().getIdentity())
                    .withRel("warehouse"));
        }

        return new Resource<>(inventory);
    }

    private ResourceSupport getCommandsResources(Long id) {
        Inventory inventory = new Inventory();
        inventory.setIdentity(id);
        return new Resource<>(inventory.getCommands());
    }
}
