package com.devcircus.java.microservices.mesp.loadsimulator;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.config.SpelExpressionConverterConfiguration;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableBinding(Processor.class)
@Import(SpelExpressionConverterConfiguration.class)
@EnableConfigurationProperties(LoadSimulatorProperties.class)
public class LoadSimulatorConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
