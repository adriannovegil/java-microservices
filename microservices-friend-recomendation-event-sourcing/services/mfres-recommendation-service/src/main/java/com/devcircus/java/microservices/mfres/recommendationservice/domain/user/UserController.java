package com.devcircus.java.microservices.mfres.recommendationservice.domain.user;

import com.devcircus.java.microservices.mfres.recommendationservice.domain.friend.FriendRepository;
import com.devcircus.java.microservices.mfres.recommendationservice.domain.friend.entity.RankedUser;
import com.devcircus.java.microservices.mfres.recommendationservice.domain.user.entity.User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1")
public class UserController {

    private final FriendRepository friendRepository;

    public UserController(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @GetMapping(path = "/users/{userId}/commands/findMutualFriends")
    public Flux<User> getMutualFriends(@PathVariable Long userId, @RequestParam Long friendId) {
        return Flux.fromIterable(friendRepository.mutualFriends(userId, friendId));
    }

    @GetMapping(path = "/users/{userId}/commands/recommendFriends")
    public Flux<RankedUser> recommendFriends(@PathVariable Long userId) {
        return Flux.fromIterable(friendRepository.recommendedFriends(userId));
    }
}
