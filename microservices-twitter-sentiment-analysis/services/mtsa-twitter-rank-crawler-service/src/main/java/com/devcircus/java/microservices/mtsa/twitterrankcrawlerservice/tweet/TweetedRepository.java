package com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.tweet;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface TweetedRepository extends Neo4jRepository<Tweeted, Long> {

    /**
     * 
     * @param tweeted 
     */
    @Query("FOREACH(x in {tweeted} | MERGE (a:User { profileId: x.user.profileId })\n"
            + "MERGE (b:Tweet { profileId: x.tweet.profileId, tweetId: x.tweet.tweetId })\n"
            + "MERGE (a)-[:TWEETED]->(b))")
    void saveTweetedRelationships(@Param("tweeted") Set<Tweeted> tweeted);
}
