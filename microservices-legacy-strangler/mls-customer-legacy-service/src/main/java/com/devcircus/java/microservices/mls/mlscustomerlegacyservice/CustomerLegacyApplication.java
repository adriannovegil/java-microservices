package com.devcircus.java.microservices.mls.mlscustomerlegacyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CustomerLegacyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerLegacyApplication.class, args);
    }
}
