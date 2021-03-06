package com.devcircus.java.microservices.mmpp.ratingservice.data.domain.entity;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Product {

    @GraphId
    private Long id;

    public Product() {
    }

    @Indexed
    private String knownId;

    public String getKnownId() {
        return knownId;
    }

    public void setKnownId(String knownId) {
        this.knownId = knownId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;

        return !(id != null ? !id.equals(product.id) : product.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Product{"
                + "id=" + id
                + '}';
    }
}
