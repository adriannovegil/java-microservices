package com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.user;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "following", itemResourceRel = "following", path = "following")
public interface FollowsRepository extends Neo4jRepository<Follows, Long> {

    /**
     * 
     * @param follows 
     */
    @Query("FOREACH(x in {follows} | MERGE (a:User { profileId: x.userA.profileId })\n"
            + "MERGE (b:User { profileId: x.userB.profileId })\n"
            + "MERGE (a)-[:FOLLOWS]->(b))")
    void saveFollows(@Param("follows") Set<Follows> follows);
}
