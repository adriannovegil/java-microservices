package com.devcircus.java.microservices.mes.orderservice.invoice;

import com.devcircus.java.microservices.mes.orderservice.address.Address;
import com.devcircus.java.microservices.mes.orderservice.address.AddressType;
import com.devcircus.java.microservices.mes.orderservice.data.BaseEntity;
import com.devcircus.java.microservices.mes.orderservice.order.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class Invoice extends BaseEntity {

    private String invoiceId, customerId;
    private List<Order> orders = new ArrayList<Order>();
    private Address billingAddress;
    private InvoiceStatus invoiceStatus;

    public Invoice(String customerId, Address billingAddress) {
        this.customerId = customerId;
        this.billingAddress = billingAddress;
        this.billingAddress.setAddressType(AddressType.BILLING);
        this.invoiceStatus = InvoiceStatus.CREATED;
    }

    @Id
    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    @Override
    public String toString() {
        return "Invoice{"
                + "invoiceId='" + invoiceId + '\''
                + ", customerId='" + customerId + '\''
                + ", orders=" + orders
                + ", billingAddress=" + billingAddress
                + ", invoiceStatus=" + invoiceStatus
                + "} " + super.toString();
    }
}
