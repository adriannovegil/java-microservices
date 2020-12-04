package com.kennybastani.guides.customer_service;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public GetCustomerResponse createGetCustomerResponse() {
        return new GetCustomerResponse();
    }

    public Customer createCustomer() {
        return new Customer();
    }

    public GetCustomerRequest createGetCustomerRequest() {
        return new GetCustomerRequest();
    }

    public UpdateCustomerRequest createUpdateCustomerRequest() {
        return new UpdateCustomerRequest();
    }

    public UpdateCustomerResponse createUpdateCustomerResponse() {
        return new UpdateCustomerResponse();
    }

}
