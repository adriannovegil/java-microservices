package com.devcircus.java.microservices.mesp.accountworker.function;

import demo.account.Account;
import demo.account.AccountStatus;
import demo.event.AccountEvent;
import demo.event.AccountEventType;
import org.apache.log4j.Logger;
import org.springframework.statemachine.StateContext;

import java.util.function.Function;

public class UnsuspendAccount extends AccountFunction {

    final private Logger log = Logger.getLogger(UnsuspendAccount.class);

    public UnsuspendAccount(StateContext<AccountStatus, AccountEventType> context, Function<AccountEvent, Account> lambda) {
        super(context, lambda);
    }

    @Override
    public Account apply(AccountEvent event) {
        log.info("Executing workflow for a unsuspended account...");
        return super.apply(event);
    }
}
