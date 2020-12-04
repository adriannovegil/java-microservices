package demo.warehouse.event;

import demo.domain.AbstractEntity;
import demo.warehouse.domain.Warehouse;

public class WarehouseEvent extends AbstractEntity {

    private WarehouseEventType type;

    public WarehouseEvent() {
    }

    public WarehouseEvent(WarehouseEventType type) {
        this.type = type;
    }

    public WarehouseEventType getType() {
        return type;
    }

    public void setType(WarehouseEventType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "WarehouseEvent{"
                + "type=" + type
                + "} " + super.toString();
    }
}
