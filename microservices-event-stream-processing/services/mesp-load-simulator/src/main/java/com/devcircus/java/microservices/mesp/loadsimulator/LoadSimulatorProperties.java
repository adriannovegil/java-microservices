package com.devcircus.java.microservices.mesp.loadsimulator;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class LoadSimulatorProperties {

    private Domain domain = Domain.ACCOUNT;
    private Operation operation = Operation.CREATE;
    private Command command;
    private Long range = 1L;

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Long getRange() {
        return range;
    }

    public void setRange(Long range) {
        this.range = range;
    }
}
