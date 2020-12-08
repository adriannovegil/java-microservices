package com.devcircus.java.microservices.mesp.loadsimulator.account.domain;

public class AccountEvent {

    private AccountEventType type;

    public AccountEvent() {
    }

    public AccountEvent(AccountEventType type) {
        this.type = type;
    }

    public AccountEventType getType() {
        return type;
    }

    public void setType(AccountEventType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AccountEvent{"
                + "type=" + type
                + "} " + super.toString();
    }
}
