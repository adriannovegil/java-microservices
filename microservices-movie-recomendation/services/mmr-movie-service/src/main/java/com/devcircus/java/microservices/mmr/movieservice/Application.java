package com.devcircus.java.microservices.mmr.movieservice;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;

@SpringBootApplication
@EnableNeo4jRepositories
@EnableDiscoveryClient
@EnableZuulProxy
public class Application extends Neo4jConfiguration {

    public Application() {
        setBasePackage("data");
    }

    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase("target/movie.db");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
