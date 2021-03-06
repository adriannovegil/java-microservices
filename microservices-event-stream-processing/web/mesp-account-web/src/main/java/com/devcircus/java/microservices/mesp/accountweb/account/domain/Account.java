package com.devcircus.java.microservices.mesp.accountweb.account.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import demo.account.action.*;
import demo.account.controller.AccountController;
import demo.account.event.AccountEvent;
import demo.domain.AbstractEntity;
import demo.domain.Aggregate;
import demo.domain.Command;
import demo.domain.Module;
import demo.order.domain.Order;
import demo.order.domain.Orders;
import org.springframework.hateoas.Link;

import javax.persistence.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Entity
public class Account extends AbstractEntity<AccountEvent, Long> {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    @Enumerated(value = EnumType.STRING)
    private AccountStatus status;

    public Account() {
        status = AccountStatus.ACCOUNT_CREATED;
    }

    public Account(String firstName, String lastName, String email) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @JsonProperty("accountId")
    @Override
    public Long getIdentity() {
        return this.id;
    }

    @Override
    public void setIdentity(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    @JsonIgnore
    public Orders getOrders() {
        return getAction(GetOrders.class)
                .apply(this);
    }

    @Command(method = "activate", controller = AccountController.class)
    public Account activate() {
        return getAction(ActivateAccount.class)
                .apply(this);
    }

    @Command(method = "archive", controller = AccountController.class)
    public Account archive() {
        return getAction(ArchiveAccount.class)
                .apply(this);
    }

    @Command(method = "confirm", controller = AccountController.class)
    public Account confirm() {
        return getAction(ConfirmAccount.class)
                .apply(this);
    }

    @Command(method = "suspend", controller = AccountController.class)
    public Account suspend() {
        return getAction(SuspendAccount.class)
                .apply(this);
    }

    @Command(method = "postOrder", controller = AccountController.class)
    public Order postOrder(Order order) {
        return getAction(PostOrder.class)
                .apply(this, order);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Module<A>, A extends Aggregate<AccountEvent, Long>> T getModule() throws
            IllegalArgumentException {
        AccountModule accountProvider = getModule(AccountModule.class);
        return (T) accountProvider;
    }

    @Override
    public Link getId() {
        return linkTo(AccountController.class)
                .slash("accounts")
                .slash(getIdentity())
                .withSelfRel();
    }

    @Override
    public String toString() {
        return "Account{"
                + "id=" + id
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", email='" + email + '\''
                + ", status=" + status
                + "} " + super.toString();
    }
}
