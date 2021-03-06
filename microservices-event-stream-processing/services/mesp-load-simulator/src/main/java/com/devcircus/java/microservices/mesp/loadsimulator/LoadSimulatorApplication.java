package com.devcircus.java.microservices.mesp.loadsimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class LoadSimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoadSimulatorApplication.class, args);
    }

}
