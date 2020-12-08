package com.devcircus.java.microservices.mesp.springbootstarterawslambda.function;

import com.devcircus.java.microservices.mesp.springbootstarterawslambda.event.PaymentEvent;
import com.devcircus.java.microservices.mesp.springbootstarterawslambda.event.PaymentEventType;
import com.devcircus.java.microservices.mesp.springbootstarterawslambda.payment.Payment;
import com.devcircus.java.microservices.mesp.springbootstarterawslambda.payment.PaymentStatus;
import org.apache.log4j.Logger;
import org.springframework.statemachine.StateContext;

import java.util.function.Function;

public abstract class PaymentFunction {

    final private Logger log = Logger.getLogger(PaymentFunction.class);
    final protected StateContext<PaymentStatus, PaymentEventType> context;
    final protected Function<PaymentEvent, Payment> lambda;

    public PaymentFunction(StateContext<PaymentStatus, PaymentEventType> context,
            Function<PaymentEvent, Payment> lambda) {
        this.context = context;
        this.lambda = lambda;
    }

    public Payment apply(PaymentEvent event) {
        // Execute the lambda function
        Payment result = lambda.apply(event);
        context.getExtendedState().getVariables().put("payment", result);
        return result;
    }
}
