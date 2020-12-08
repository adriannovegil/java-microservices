package com.devcircus.java.microservices.mes.cartservice.order;

import com.devcircus.java.microservices.mes.cartservice.address.Address;
import com.devcircus.java.microservices.mes.cartservice.address.AddressType;
import com.devcircus.java.microservices.mes.cartservice.data.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order extends BaseEntity implements Serializable {

    @JsonProperty(value = "orderId")
    private String orderId;
    private String accountNumber;
    private OrderStatus orderStatus;
    private List<LineItem> lineItems = new ArrayList<>();
    private Address shippingAddress;

    public Order() {
    }

    public Order(String accountNumber, Address shippingAddress) {
        this.accountNumber = accountNumber;
        this.shippingAddress = shippingAddress;
        this.shippingAddress.setAddressType(AddressType.SHIPPING);
        this.orderStatus = OrderStatus.PURCHASED;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void addLineItem(LineItem lineItem) {
        lineItems.add(lineItem);
    }

    @Override
    public String toString() {
        return "Order{"
                + "orderId='" + orderId + '\''
                + ", accountNumber='" + accountNumber + '\''
                + ", orderStatus=" + orderStatus
                + ", lineItems=" + lineItems
                + ", shippingAddress=" + shippingAddress
                + "} " + super.toString();
    }
}
