package com.devcircus.java.microservices.mes.catalogservice.product;

import java.io.Serializable;

public class Product implements Serializable {

    private Long id;
    private String name, productId, description;
    private Double unitPrice;
    private Boolean inStock;

    /**
     * 
     */
    public Product() {
    }

    /**
     * 
     * @param name
     * @param productId
     * @param unitPrice 
     */
    public Product(String name, String productId, Double unitPrice) {
        this.name = name;
        this.productId = productId;
        this.unitPrice = unitPrice;
    }

    /**
     * 
     * @return 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return 
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 
     * @param productId 
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 
     * @return 
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description 
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return 
     */
    public Double getUnitPrice() {
        return unitPrice;
    }

    /**
     * 
     * @param unitPrice 
     */
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 
     * @return 
     */
    public Boolean getInStock() {
        return inStock;
    }

    /**
     * 
     * @param inStock 
     */
    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Product{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", productId='" + productId + '\''
                + ", description='" + description + '\''
                + ", unitPrice=" + unitPrice
                + ", inStock=" + inStock
                + '}';
    }
}
