package com.devcircus.java.microservices.mesp.accountworker.function;

import demo.account.Account;
import demo.account.AccountStatus;
import demo.event.AccountEvent;
import demo.event.AccountEventType;
import org.apache.log4j.Logger;
import org.springframework.statemachine.StateContext;

import java.util.function.Function;

public abstract class AccountFunction {

    final private Logger log = Logger.getLogger(AccountFunction.class);
    final protected StateContext<AccountStatus, AccountEventType> context;
    final protected Function<AccountEvent, Account> lambda;

    public AccountFunction(StateContext<AccountStatus, AccountEventType> context,
            Function<AccountEvent, Account> lambda) {
        this.context = context;
        this.lambda = lambda;
    }

    public Account apply(AccountEvent event) {
        // Execute the lambda function
        Account result = lambda.apply(event);
        context.getExtendedState().getVariables().put("account", result);
        return result;
    }
}
