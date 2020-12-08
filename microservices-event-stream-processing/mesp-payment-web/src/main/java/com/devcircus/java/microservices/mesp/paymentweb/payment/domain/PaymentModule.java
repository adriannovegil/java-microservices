package com.devcircus.java.microservices.mesp.paymentweb.payment.domain;

@org.springframework.stereotype.Service
public class PaymentModule extends Module<Payment> {

    private final PaymentService paymentService;
    private final EventService<, Long> eventService;

    public PaymentModule(PaymentService paymentService, EventService<PaymentEvent, Long> eventService) {
        this.paymentService = paymentService;
        this.eventService = eventService;
    }

    @Override
    public PaymentService getDefaultService() {
        return paymentService;
    }

    @Override
    public EventService<PaymentEvent, Long> getDefaultEventService() {
        return eventService;
    }
}
