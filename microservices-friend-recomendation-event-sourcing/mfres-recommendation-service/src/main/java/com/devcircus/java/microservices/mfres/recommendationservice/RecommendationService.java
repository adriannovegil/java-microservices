package io.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories(value = {"io.example.domain.user", "io.example.domain.friend"})
@EntityScan({"io.example.domain.friend.entity", "io.example.domain.user.entity"})
public class RecommendationService {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RecommendationService.class).run(args);
    }
}
