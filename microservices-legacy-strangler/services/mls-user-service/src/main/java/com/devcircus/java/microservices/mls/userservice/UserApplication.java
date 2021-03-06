package com.devcircus.java.microservices.mls.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
@EnableEurekaClient
@EnableOAuth2Client
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    @ConfigurationProperties("security.oauth2.client")
    public ClientCredentialsResourceDetails oauth2ClientCredentialsResourceDetails() {
        return new ClientCredentialsResourceDetails();
    }

    @LoadBalanced
    @Bean
    public OAuth2RestTemplate loadBalancedOauth2RestTemplate(ClientCredentialsResourceDetails resource) {
        ClientCredentialsResourceDetails clientCredentialsResourceDetails = new ClientCredentialsResourceDetails();
        clientCredentialsResourceDetails.setAccessTokenUri(resource.getAccessTokenUri());
        clientCredentialsResourceDetails.setClientId(resource.getClientId());
        clientCredentialsResourceDetails.setClientSecret(resource.getClientSecret());
        clientCredentialsResourceDetails.setClientAuthenticationScheme(resource.getClientAuthenticationScheme());
        clientCredentialsResourceDetails.setScope(resource.getScope());
        clientCredentialsResourceDetails.setGrantType(resource.getGrantType());
        OAuth2RestTemplate auth2RestTemplate = new OAuth2RestTemplate(clientCredentialsResourceDetails);
        auth2RestTemplate.setAccessTokenProvider(new ClientCredentialsAccessTokenProvider());
        return auth2RestTemplate;
    }

    @Component
    public static class CustomizedRestMvcConfiguration extends RepositoryRestConfigurerAdapter {

        @Override
        public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
            config.setBasePath("/api");
        }
    }
}
