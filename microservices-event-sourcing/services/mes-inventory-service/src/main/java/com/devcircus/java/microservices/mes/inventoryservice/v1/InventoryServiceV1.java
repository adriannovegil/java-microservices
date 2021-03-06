package com.devcircus.java.microservices.mes.inventoryservice.v1;

import com.devcircus.java.microservices.mes.inventoryservice.inventory.Inventory;
import com.devcircus.java.microservices.mes.inventoryservice.inventory.InventoryRepository;
import com.devcircus.java.microservices.mes.inventoryservice.product.Product;
import com.devcircus.java.microservices.mes.inventoryservice.product.ProductRepository;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class InventoryServiceV1 {

    private InventoryRepository inventoryRepository;
    private ProductRepository productRepository;
    private Session neo4jTemplate;

    @Autowired
    public InventoryServiceV1(InventoryRepository inventoryRepository,
            ProductRepository productRepository, Session neo4jTemplate) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.neo4jTemplate = neo4jTemplate;
    }

    public Product getProduct(String productId) {
        Product product;

        product = productRepository.getProductByProductId(productId);

        if (product != null) {
            Stream<Inventory> availableInventory = inventoryRepository.getAvailableInventoryForProduct(productId).stream();
            product.setInStock(availableInventory.findAny().isPresent());
        }

        return product;
    }

    public List<Inventory> getAvailableInventoryForProductIds(String productIds) {
        List<Inventory> inventoryList;

        inventoryList = inventoryRepository.getAvailableInventoryForProductList(productIds.split(","));

        return neo4jTemplate.loadAll(inventoryList, 1)
                .stream().collect(Collectors.toList());
    }
}
