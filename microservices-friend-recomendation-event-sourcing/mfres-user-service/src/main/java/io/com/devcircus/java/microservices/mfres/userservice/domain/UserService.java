package io.com.devcircus.java.microservices.mfres.userservice.domain;

import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.data.r2dbc.function.TransactionalDatabaseClient;
import org.springframework.data.r2dbc.function.convert.MappingR2dbcConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@Service
public class UserService {

    private final TransactionalDatabaseClient transactionalDatabaseClient;
    private final DatabaseClient databaseClient;
    private final MappingR2dbcConverter converter;

    public UserService(TransactionalDatabaseClient transactionalDatabaseClient, DatabaseClient databaseClient,
            MappingR2dbcConverter converter) {
        this.transactionalDatabaseClient = transactionalDatabaseClient;
        this.databaseClient = databaseClient;
        this.converter = converter;
    }

    public Mono<User> create(User user, Consumer<User> callback) {
        return transactionalDatabaseClient.inTransaction(db -> db.insert().into(User.class)
                .using(user)
                .map((o, u) -> converter.populateIdIfNecessary(user).apply(o, u))
                .first()
                .map(User::getId)
                .flatMap(id -> db.execute().sql("SELECT * FROM users WHERE id=$1")
                .bind(0, id).as(User.class)
                .fetch()
                .first()).delayUntil(u -> Mono.fromRunnable(() -> callback.accept(u)))).single();
    }

    public Mono<User> find(Long id) {
        return databaseClient.execute().sql("SELECT * FROM users WHERE id=$1")
                .bind(0, id).as(User.class)
                .fetch()
                .one()
                .single();
    }

    public Mono<User> update(User user, Consumer<User> callback) {
        Assert.notNull(user.getId(), "User ID must not be null");

        AtomicReference<Long> userId = new AtomicReference<>();
        userId.set(user.getId());

        return transactionalDatabaseClient.inTransaction(db
                -> db.execute().sql("UPDATE users SET first_name=$1, last_name=$2 WHERE id=$3 RETURNING *")
                        .bind(0, user.getFirstName())
                        .bind(1, user.getLastName())
                        .bind(2, user.getId()).as(User.class).fetch().rowsUpdated()
                        .then(db.execute().sql("SELECT * FROM users WHERE id=$1")
                                .bind(0, userId.get())
                                .as(User.class)
                                .fetch()
                                .first()).delayUntil(u -> Mono.fromRunnable(() -> callback.accept(u)))).single();
    }
}
