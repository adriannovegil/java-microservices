package com.devcircus.java.microservices.mesp.paymentweb.payment.domain;

import com.devcircus.java.microservices.mesp.paymentweb.domain.AbstractEntity;
import com.devcircus.java.microservices.mesp.paymentweb.payment.action.ConnectOrder;
import com.devcircus.java.microservices.mesp.paymentweb.payment.controller.PaymentController;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;

import javax.persistence.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Entity
public class Payment extends AbstractEntity<PaymentEvent, Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;

    private Double amount;
    private Long orderId;

    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    public Payment() {
        status = PaymentStatus.PAYMENT_CREATED;
    }

    public Payment(Double amount, PaymentMethod paymentMethod) {
        this();
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    @JsonProperty("paymentId")
    @Override
    public Long getIdentity() {
        return this.id;
    }

    @Override
    public void setIdentity(Long id) {
        this.id = id;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Command(method = "connectOrder", controller = PaymentController.class)
    public Payment connectOrder(Long orderId) {
        return getAction(ConnectOrder.class)
                .apply(this, orderId);
    }

    @Command(method = "processPayment", controller = PaymentController.class)
    public Payment processPayment() {
        return getAction(ProcessPayment.class)
                .apply(this);
    }

    @Override
    public Link getId() {
        return linkTo(PaymentController.class)
                .slash("payments")
                .slash(getIdentity())
                .withSelfRel();
    }
}
