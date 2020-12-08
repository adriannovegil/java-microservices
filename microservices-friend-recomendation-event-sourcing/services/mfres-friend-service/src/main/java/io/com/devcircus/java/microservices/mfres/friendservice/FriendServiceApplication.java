package io.com.devcircus.java.microservices.mfres.friendservice;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class FriendServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FriendServiceApplication.class).web(WebApplicationType.REACTIVE).run(args);
    }

    @Configuration
    @EnableBinding(Source.class)
    class StreamConfig {
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder userWebClient() {
        return WebClient.builder();
    }

}
