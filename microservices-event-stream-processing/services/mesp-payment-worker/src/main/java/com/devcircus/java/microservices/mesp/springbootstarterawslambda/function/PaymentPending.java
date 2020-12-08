package com.devcircus.java.microservices.mesp.springbootstarterawslambda.function;

import com.devcircus.java.microservices.mesp.springbootstarterawslambda.event.PaymentEvent;
import com.devcircus.java.microservices.mesp.springbootstarterawslambda.event.PaymentEventType;
import com.devcircus.java.microservices.mesp.springbootstarterawslambda.payment.Payment;
import com.devcircus.java.microservices.mesp.springbootstarterawslambda.payment.PaymentStatus;
import org.apache.log4j.Logger;
import org.springframework.statemachine.StateContext;

import java.util.function.Function;

public class PaymentPending extends PaymentFunction {

    final private Logger log = Logger.getLogger(PaymentPending.class);

    public PaymentPending(StateContext<PaymentStatus, PaymentEventType> context, Function<PaymentEvent, Payment> lambda) {
        super(context, lambda);
    }

    @Override
    public Payment apply(PaymentEvent event) {
        log.info("Executing workflow for payment pending...");
        return super.apply(event);
    }
}
