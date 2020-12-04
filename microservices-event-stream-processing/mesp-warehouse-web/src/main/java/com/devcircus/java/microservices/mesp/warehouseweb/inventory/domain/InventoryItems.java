package demo.inventory.domain;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

public class InventoryItems extends Resources<Inventory> {

    public InventoryItems() {
    }

    public InventoryItems(Iterable<Inventory> content, Link... links) {
        super(content, links);
    }
}
