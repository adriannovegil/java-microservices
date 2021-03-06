package com.devcircus.java.microservices.mesp.warehouseweb.inventory.domain;

import com.devcircus.java.microservices.mesp.warehouseweb.inventory.action.ReserveInventory;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.action.UpdateInventoryStatus;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.controller.InventoryController;
import com.devcircus.java.microservices.mesp.warehouseweb.inventory.event.InventoryEvent;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.domain.Reservation;
import com.devcircus.java.microservices.mesp.warehouseweb.warehouse.domain.Warehouse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;

import javax.persistence.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Entity
public class Inventory extends AbstractEntity<InventoryEvent, Long> {

    @Id
    @GeneratedValue
    private Long id;

    private String productId;

    @Enumerated(value = EnumType.STRING)
    private InventoryStatus status;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Reservation reservation;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Warehouse warehouse;

    public Inventory() {
        this.status = InventoryStatus.INVENTORY_CREATED;
    }

    @JsonProperty("inventoryId")
    @Override
    public Long getIdentity() {
        return this.id;
    }

    @Override
    public void setIdentity(Long id) {
        this.id = id;
    }

    public InventoryStatus getStatus() {
        return status;
    }

    public void setStatus(InventoryStatus status) {
        this.status = status;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Command(method = "reserve", controller = InventoryController.class)
    public Inventory reserve(Long reservationId) {
        return getAction(ReserveInventory.class)
                .apply(this, reservationId);
    }

    @Command(method = "updateInventoryStatus", controller = InventoryController.class)
    public Inventory updateStatus(InventoryStatus status) {
        return getAction(UpdateInventoryStatus.class)
                .apply(this, status);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Module<A>, A extends Aggregate<InventoryEvent, Long>> T getModule() throws
            IllegalArgumentException {
        InventoryModule inventoryModule = getModule(InventoryModule.class);
        return (T) inventoryModule;
    }

    @Override
    public Link getId() {
        Link link;
        try {
            link = linkTo(InventoryController.class)
                    .slash("inventory")
                    .slash(getIdentity())
                    .withSelfRel();
        } catch (Exception ex) {
            link = new Link("http://warehouse-service/v1/inventory/" + id, "self");
        }

        return link;
    }
}
