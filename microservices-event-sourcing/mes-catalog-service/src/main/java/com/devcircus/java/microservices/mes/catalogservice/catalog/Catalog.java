package com.devcircus.java.microservices.mes.catalogservice.catalog;

import com.devcircus.java.microservices.mes.catalogservice.product.Product;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Catalog implements Serializable {

    private Long id;
    private Long catalogNumber;
    private Set<Product> products = new HashSet<>();
    private String name;

    /**
     *
     */
    public Catalog() {
    }

    /**
     *
     * @param name
     */
    public Catalog(String name) {
        this.name = name;
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
    public Set<Product> getProducts() {
        return products;
    }

    /**
     *
     * @param products
     */
    public void setProducts(Set<Product> products) {
        this.products = products;
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
    public Long getCatalogNumber() {
        return catalogNumber;
    }

    /**
     *
     * @param catalogNumber
     */
    public void setCatalogNumber(Long catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Catalog{"
                + "id=" + id
                + ", catalogNumber=" + catalogNumber
                + ", products=" + products
                + ", name='" + name + '\''
                + '}';
    }
}
