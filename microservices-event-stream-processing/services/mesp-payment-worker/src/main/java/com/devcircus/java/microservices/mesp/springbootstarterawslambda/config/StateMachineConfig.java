package com.devcircus.java.microservices.mesp.springbootstarterawslambda.config;

import com.devcircus.java.microservices.mesp.springbootstarterawslambda.event.PaymentEvent;
import com.devcircus.java.microservices.mesp.springbootstarterawslambda.event.PaymentEventType;
import com.devcircus.java.microservices.mesp.springbootstarterawslambda.function.OrderConnected;
import com.devcircus.java.microservices.mesp.springbootstarterawslambda.function.PaymentCreated;
import com.devcircus.java.microservices.mesp.springbootstarterawslambda.function.PaymentFailed;
import com.devcircus.java.microservices.mesp.springbootstarterawslambda.function.PaymentFunction;
import com.devcircus.java.microservices.mesp.springbootstarterawslambda.function.PaymentPending;
import com.devcircus.java.microservices.mesp.springbootstarterawslambda.function.PaymentProcessed;
import com.devcircus.java.microservices.mesp.springbootstarterawslambda.function.PaymentSucceeded;
import com.devcircus.java.microservices.mesp.springbootstarterawslambda.payment.Payment;
import com.devcircus.java.microservices.mesp.springbootstarterawslambda.payment.PaymentStatus;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<PaymentStatus, PaymentEventType> {

    final private Logger log = Logger.getLogger(StateMachineConfig.class);

    @Override
    public void configure(StateMachineStateConfigurer<PaymentStatus, PaymentEventType> states) {
        try {
            // Describe the initial condition of the payment state machine
            states.withStates()
                    .initial(PaymentStatus.PAYMENT_CREATED)
                    .states(EnumSet.allOf(PaymentStatus.class));
        } catch (Exception e) {
            throw new RuntimeException("State machine configuration failed", e);
        }
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<PaymentStatus, PaymentEventType> transitions) {
        try {
            // Describe state machine transitions for payments
            transitions.withExternal()
                    .source(PaymentStatus.PAYMENT_CREATED)
                    .target(PaymentStatus.PAYMENT_CREATED)
                    .event(PaymentEventType.PAYMENT_CREATED)
                    .action(paymentCreated())
                    .and()
                    .withExternal()
                    .source(PaymentStatus.PAYMENT_CREATED)
                    .target(PaymentStatus.ORDER_CONNECTED)
                    .event(PaymentEventType.ORDER_CONNECTED)
                    .action(orderConnected())
                    .and()
                    .withExternal()
                    .source(PaymentStatus.ORDER_CONNECTED)
                    .target(PaymentStatus.PAYMENT_PROCESSED)
                    .event(PaymentEventType.PAYMENT_PROCESSED)
                    .action(paymentProcessed())
                    .and()
                    .withExternal()
                    .source(PaymentStatus.PAYMENT_PROCESSED)
                    .target(PaymentStatus.PAYMENT_SUCCEEDED)
                    .event(PaymentEventType.PAYMENT_SUCCEEDED)
                    .action(paymentSucceeded(new RestTemplate()))
                    .and()
                    .withExternal()
                    .source(PaymentStatus.PAYMENT_PROCESSED)
                    .target(PaymentStatus.PAYMENT_FAILED)
                    .event(PaymentEventType.PAYMENT_FAILED)
                    .action(paymentFailed(new RestTemplate()));
        } catch (Exception e) {
            throw new RuntimeException("Could not configure state machine transitions", e);
        }
    }

    private PaymentEvent applyEvent(StateContext<PaymentStatus, PaymentEventType> context, PaymentFunction paymentFunction) {
        PaymentEvent paymentEvent = null;

        // Log out the progress of the state machine replication
        log.info("Replicate event: " + context.getMessage().getPayload());

        // The machine is finished replicating when an PaymentEvent is found in the message header
        if (context.getMessageHeader("event") != null) {
            paymentEvent = (PaymentEvent) context.getMessageHeader("event");
            log.info("State machine replicated: " + paymentEvent.getType());

            // Apply the provided function to the PaymentEvent
            paymentFunction.apply(paymentEvent);
        }

        return paymentEvent;
    }

    @Bean
    public Action<PaymentStatus, PaymentEventType> paymentCreated() {
        return context -> applyEvent(context,
                new PaymentCreated(context, event -> {
                    log.info(event.getType() + ": " + event.getLink("payment").getHref());
                    // Get the payment resource for the event
                    Traverson traverson = new Traverson(
                            URI.create(event.getLink("payment").getHref()),
                            MediaTypes.HAL_JSON
                    );

                    return traverson.follow("self")
                            .toEntity(Payment.class)
                            .getBody();
                }));
    }

    @Bean
    public Action<PaymentStatus, PaymentEventType> orderConnected() {
        return context -> applyEvent(context,
                new OrderConnected(context, event -> {
                    log.info(event.getType() + ": " + event.getLink("payment").getHref());
                    // Get the payment resource for the event
                    Traverson traverson = new Traverson(
                            URI.create(event.getLink("payment").getHref()),
                            MediaTypes.HAL_JSON
                    );

                    return traverson.follow("self")
                            .toEntity(Payment.class)
                            .getBody();
                }));
    }

    @Bean
    public Action<PaymentStatus, PaymentEventType> paymentPending() {
        return context -> applyEvent(context,
                new PaymentPending(context, event -> {
                    log.info(event.getType() + ": " + event.getLink("payment").getHref());
                    // Get the payment resource for the event
                    Traverson traverson = new Traverson(
                            URI.create(event.getLink("payment").getHref()),
                            MediaTypes.HAL_JSON
                    );

                    return traverson.follow("self")
                            .toEntity(Payment.class)
                            .getBody();
                }));
    }

    @Bean
    public Action<PaymentStatus, PaymentEventType> paymentProcessed() {
        return context -> applyEvent(context,
                new PaymentProcessed(context, event -> {
                    log.info(event.getType() + ": " + event.getLink("payment").getHref());
                    // Get the payment resource for the event
                    Traverson traverson = new Traverson(
                            URI.create(event.getLink("payment").getHref()),
                            MediaTypes.HAL_JSON
                    );

                    return traverson.follow("self")
                            .toEntity(Payment.class)
                            .getBody();
                }));
    }

    @Bean
    public Action<PaymentStatus, PaymentEventType> paymentSucceeded(RestTemplate restTemplate) {
        return context -> applyEvent(context,
                new PaymentSucceeded(context, event -> {
                    log.info(event.getType() + ": " + event.getLink("payment").getHref());
                    return updatePaymentStatus(restTemplate, event, PaymentStatus.PAYMENT_SUCCEEDED);
                }));
    }

    @Bean
    public Action<PaymentStatus, PaymentEventType> paymentFailed(RestTemplate restTemplate) {
        return context -> applyEvent(context,
                new PaymentFailed(context, event -> {
                    log.info(event.getType() + ": " + event.getLink("payment").getHref());
                    return updatePaymentStatus(restTemplate, event, PaymentStatus.PAYMENT_FAILED);
                }));
    }

    private Payment updatePaymentStatus(RestTemplate restTemplate, PaymentEvent event, PaymentStatus status) {
        // Get the payment resource for the event
        Traverson traverson = new Traverson(
                URI.create(event.getLink("payment").getHref()),
                MediaTypes.HAL_JSON
        );

        Payment payment = traverson.follow("self")
                .toEntity(Payment.class)
                .getBody();

        payment.setStatus(status);
        restTemplate.put(payment.getLink("self").getHref(), payment);
        return payment;
    }
}
