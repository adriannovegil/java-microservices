package com.devcircus.java.microservices.mls.profileservice.message;

import com.devcircus.java.microservices.mls.profileservice.customer.CustomerClient;
import com.devcircus.java.microservices.mls.profileservice.profile.Profile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kennybastani.guides.customer_service.UpdateCustomerResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.AmqpIllegalStateException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.rmi.UnexpectedException;

@Component
public class Receiver {

    private final Log log = LogFactory.getLog(Receiver.class);
    private final ObjectMapper objectMapper;
    private final CustomerClient customerClient;

    @Autowired
    public Receiver(ObjectMapper objectMapper, CustomerClient customerClient) {
        this.objectMapper = objectMapper;
        this.customerClient = customerClient;
    }

    @RabbitListener(queues = {"customer.update"})
    public void updateCustomer(String message) throws InterruptedException, IOException {
        Profile profile = objectMapper.readValue(message, Profile.class);

        try {
            // Update the customer service for the profile
            UpdateCustomerResponse response
                    = customerClient.updateCustomerResponse(profile);

            if (!response.isSuccess()) {
                String errorMsg
                        = String.format("Could not update customer from profile for %s",
                                profile.getUsername());
                log.error(errorMsg);
                throw new UnexpectedException(errorMsg);
            }
        } catch (Exception ex) {
            // Throw AMQP exception and redeliver the message
            throw new AmqpIllegalStateException("Customer service update failed", ex);
        }
    }

}
