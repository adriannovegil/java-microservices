package com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.text;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface TextEntityRepository extends Neo4jRepository<TextEntity, Long> {

    @Query("WITH timestamp() as time\n"
            + "MATCH (entity:TextEntity)<-[:HAS_ENTITY]-(t:Tweet)\n"
            + "WHERE (exists(entity.pagerank) AND "
            + "((time - coalesce(entity.lastCategorizedAt, time - 3000001) > 3000000))\n"
            + "AND NOT entity.name  =~ \"(?ism)(http.*|RT.*|@.*|\\\\d*|#.*|.*http.*|.*@.*|\\\\w|\\\\W.*)\")\n"
            + "WITH entity, count(DISTINCT t) as tweets\n"
            + "WHERE tweets > 3\n"
            + "RETURN entity\n"
            + "ORDER BY entity.pagerank DESC\n"
            + "LIMIT 1\n")
    TextEntity findUncategorizedTextEntity(Long time);

    @Query("MATCH (entity:TextEntity { name: {name} })\n"
            + "WITH entity\n"
            + "FOREACH (x in {categories} | MERGE (category:Category { name: x })\n"
            + "   MERGE (category)<-[:HAS_CATEGORY]-(entity))\n"
            + "SET entity.lastCategorizedAt = timestamp()")
    void saveTextEntityCategories(String name, List<String> categories);

    @Query("MATCH (e:TextEntity)\n"
            + "WHERE NOT e.name  =~ \"(?ism)(http.*|RT.*|@.*|\\\\d*|#.*|.*http.*|.*@.*|\\\\w|\\\\W.*)\"\n"
            + "WITH collect(e) as nodes\n"
            + "CALL apoc.algo.pageRankWithConfig(nodes,{iterations:5,types:\"HAS_ENTITY\"}) YIELD node, score\n"
            + "WITH node, score\n"
            + "SET node.pagerank = score")
    void updatePageRankForEntityGraph();
}
