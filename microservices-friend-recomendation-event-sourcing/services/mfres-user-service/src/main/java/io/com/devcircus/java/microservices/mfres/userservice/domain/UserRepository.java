package io.com.devcircus.java.microservices.mfres.userservice.domain;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.query.Query;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<User, Long> {

    @Query("SELECT * FROM users u WHERE u.id = $1 LIMIT 1")
    Mono<User> getUser(Long id);
}
