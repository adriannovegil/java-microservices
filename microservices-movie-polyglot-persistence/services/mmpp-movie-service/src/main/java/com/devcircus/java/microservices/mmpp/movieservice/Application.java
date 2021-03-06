package com.devcircus.java.microservices.mmpp.movieservice;

import com.devcircus.java.microservices.mmpp.movieservice.data.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import javax.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan(basePackages = {"service.config", "service.data"})
@EnableZuulProxy
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private RepositoryRestMvcConfiguration repositoryRestConfiguration;

    @PostConstruct
    public void postConstructConfiguration() {
        repositoryRestConfiguration.config().exposeIdsFor(Movie.class);
    }
}
