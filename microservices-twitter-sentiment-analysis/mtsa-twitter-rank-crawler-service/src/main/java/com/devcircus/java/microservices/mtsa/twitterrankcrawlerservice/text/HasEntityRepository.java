package com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.text;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface HasEntityRepository extends Neo4jRepository<HasEntity, Long> {

    @Query("FOREACH(x in {hasEntity} | MERGE (a:Tweet { profileId: x.tweet.profileId, tweetId: x.tweet.tweetId })\n"
            + "MERGE (b:TextEntity { name: x.textEntity.name })\n"
            + "MERGE (a)-[r:HAS_ENTITY { salience: x.salience, sentiment: x.sentiment, magnitude: x.magnitude }]->(b))")
    void saveHasEntityRelationships(@Param("hasEntity") Set<HasEntity> hasEntities);
}
