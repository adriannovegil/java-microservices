package io.example.domain.friend;

import io.example.domain.user.UserClient;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.data.r2dbc.function.TransactionalDatabaseClient;
import org.springframework.data.r2dbc.function.convert.MappingR2dbcConverter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class FriendService {

    private final TransactionalDatabaseClient transactionalDatabaseClient;
    private final DatabaseClient databaseClient;
    private final MappingR2dbcConverter converter;
    private final UserClient userClient;

    public FriendService(TransactionalDatabaseClient transactionalDatabaseClient, DatabaseClient databaseClient,
            MappingR2dbcConverter converter, UserClient userClient) {
        this.transactionalDatabaseClient = transactionalDatabaseClient;
        this.databaseClient = databaseClient;
        this.converter = converter;
        this.userClient = userClient;
    }

    public Mono<Friend> create(Friend friend, Function<Friend, Publisher<Void>> callback) {

        // The userId and friendId must not be the same, as users cannot befriend themselves
        return Mono.just(friend).doOnNext((f) -> {
            if (f.getFriendId().equals(f.getUserId())) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "A user cannot befriend oneself");
            }
        }).then(Mono.sequenceEqual(userClient.getUser(friend.getUserId()).hasElement(),
                userClient.getUser(friend.getFriendId()).hasElement(), (a, b) -> a && b).doOnNext(valid -> {
            if (!valid) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
                        "The supplied friends do not exist by the friendId or userId");
            }
        }).then(transactionalDatabaseClient.inTransaction(db -> db.insert().into(Friend.class)
                .using(friend)
                .map((o, u) -> converter.populateIdIfNecessary(friend).apply(o, u))
                .first().map(Friend::getId)
                .flatMap(id -> db.execute().sql("SELECT * FROM friend WHERE id=$1")
                .bind(0, id).as(Friend.class)
                .fetch()
                .first()))
                .delayUntil(callback).single()));
    }

    public Flux<Friend> findUserFriends(Long userId) {
        return databaseClient.execute().sql("SELECT * FROM friend WHERE user_id=$1 LIMIT 1")
                .bind(0, userId).as(Friend.class)
                .fetch()
                .all();
    }

    public Mono<Friend> find(Long id) {
        return databaseClient.execute().sql("SELECT * FROM friend WHERE id=$1 LIMIT 1")
                .bind(0, id).as(Friend.class)
                .fetch()
                .one();
    }

    public Mono<Friend> update(Friend friend, Consumer<Friend> callback) {
        Assert.notNull(friend.getId(), "Friend ID must not be null");

        AtomicReference<Long> friendId = new AtomicReference<>();
        friendId.set(friend.getId());

        return transactionalDatabaseClient.inTransaction(db
                -> db.execute().sql("UPDATE friend SET friend_id=$1, user_id=$2 WHERE id=$3 RETURNING *")
                        .bind(0, friend.getFriendId())
                        .bind(1, friend.getUserId())
                        .bind(2, friend.getId()).as(Friend.class).fetch().rowsUpdated()
                        .then(db.execute().sql("SELECT * FROM friend WHERE id=$1")
                                .bind(0, friendId.get())
                                .as(Friend.class)
                                .fetch()
                                .first()).delayUntil(u -> Mono.fromRunnable(() -> callback.accept(u)))).single();
    }

    public Mono<Friend> delete(Friend friend, Consumer<Friend> callback) {
        return transactionalDatabaseClient.inTransaction(db
                -> db.execute().sql("SELECT * FROM friend f WHERE f.user_id=$1 AND f.friend_id=$2 LIMIT 1")
                        .bind(0, friend.getUserId())
                        .bind(1, friend.getFriendId())
                        .as(Friend.class).fetch().first()
                        .flatMap(f -> {
                            friend.setId(f.getId());
                            return db.execute().sql("DELETE FROM friend f WHERE f.id=$1")
                                    .bind(0, f.getId())
                                    .fetch()
                                    .rowsUpdated();
                        }).single().then(Mono.just(friend))
                        .delayUntil(u -> Mono.fromRunnable(() -> callback.accept(u)))).single();
    }

    public Mono<Boolean> exists(Long userId, Long friendId) {
        return databaseClient.execute().sql("SELECT * FROM friend WHERE user_id=$1 AND friend_id=$2")
                .bind(0, userId)
                .bind(1, friendId)
                .as(Friend.class).fetch().all().hasElements();
    }
}
