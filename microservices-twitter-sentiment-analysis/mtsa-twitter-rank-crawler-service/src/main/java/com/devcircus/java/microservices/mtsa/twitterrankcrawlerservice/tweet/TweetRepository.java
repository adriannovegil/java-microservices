package com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.tweet;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface TweetRepository extends Neo4jRepository<Tweet, Long> {

    @Query("MATCH (entity:TextEntity { name: {textEntityName} }),"
            + "(entity)<-[:HAS_ENTITY]-(tweet:Tweet)"
            + "RETURN DISTINCT tweet")
    List<Tweet> findTweetsForTextEntity(String textEntityName);
}
