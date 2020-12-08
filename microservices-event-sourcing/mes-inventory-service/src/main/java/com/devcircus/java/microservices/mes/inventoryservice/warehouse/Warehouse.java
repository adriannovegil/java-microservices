package com.devcircus.java.microservices.mes.inventoryservice.warehouse;

import com.devcircus.java.microservices.mes.inventoryservice.address.Address;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Warehouse {

    @GraphId
    private Long id;

    private String name;

    @Relationship(type = "HAS_ADDRESS")
    private Address address;

    public Warehouse() {
    }

    public Warehouse(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Warehouse{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", address=" + address
                + '}';
    }
}
