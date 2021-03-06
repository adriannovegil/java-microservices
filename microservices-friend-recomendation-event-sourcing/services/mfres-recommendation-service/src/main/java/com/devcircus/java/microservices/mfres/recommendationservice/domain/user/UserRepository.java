package com.devcircus.java.microservices.mfres.recommendationservice.domain.user;

import com.devcircus.java.microservices.mfres.recommendationservice.domain.user.entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

    User findUserByUserId(Long userId);
}
