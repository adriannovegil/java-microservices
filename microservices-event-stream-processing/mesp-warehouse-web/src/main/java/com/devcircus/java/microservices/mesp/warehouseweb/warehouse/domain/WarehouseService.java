package demo.warehouse.domain;

import demo.domain.Service;
import demo.order.domain.Order;
import demo.warehouse.repository.WarehouseRepository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class WarehouseService extends Service<Warehouse, Long> {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse create(Warehouse warehouse) {
        // Save the warehouse to the repository
        warehouse = warehouseRepository.saveAndFlush(warehouse);

        return warehouse;
    }

    public Warehouse get(Long id) {
        return warehouseRepository.findOne(id);
    }

    public Warehouse update(Warehouse warehouse) {
        Assert.notNull(warehouse.getIdentity(), "Warehouse id must be present in the resource URL");
        Assert.notNull(warehouse, "Warehouse request body cannot be null");

        Assert.state(warehouseRepository.exists(warehouse.getIdentity()),
                "The warehouse with the supplied id does not exist");

        Warehouse currentWarehouse = get(warehouse.getIdentity());
        currentWarehouse.setAddress(warehouse.getAddress());
        currentWarehouse.setStatus(warehouse.getStatus());

        return warehouseRepository.saveAndFlush(currentWarehouse);
    }

    public boolean delete(Long id) {
        Assert.state(warehouseRepository.exists(id),
                "The warehouse with the supplied id does not exist");
        this.warehouseRepository.delete(id);
        return true;
    }

    public Warehouse findWarehouseForOrder(Order order) {
        List<String> productIds = order.getLineItems().stream()
                .map(LineItem::getProductId)
                .distinct()
                .collect(Collectors.toList());

        List<Warehouse> warehouses = warehouseRepository.findAllWithInventory((long) productIds.size(), productIds);

        return warehouses.stream().findFirst().orElse(null);
    }
}
