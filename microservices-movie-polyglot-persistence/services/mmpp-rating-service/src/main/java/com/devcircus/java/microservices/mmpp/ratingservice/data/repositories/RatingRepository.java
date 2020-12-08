package com.devcircus.java.microservices.mmpp.ratingservice.data.repositories;

import com.devcircus.java.microservices.mmpp.ratingservice.data.domain.rels.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "ratings", path = "ratings")
public interface RatingRepository extends
        PagingAndSortingRepository<Rating, Long> {

    @Query(value = "MATCH (n:User)-[r:Rating]->() RETURN r")
    Page<Rating> findAll(Pageable pageable);

    @Query(value = "MATCH (n:User)-[r:Rating]->() WHERE n.knownId = {id} RETURN r")
    Page<Rating> findByUserId(@Param(value = "id") String id, Pageable pageable);

    @Query(value = "MATCH (n:User)-[r:Rating]->() WHERE n.knownId = {id} RETURN avg(toFloat(r.rating))")
    Double getAverageRating(@Param(value = "id") String id);
}
