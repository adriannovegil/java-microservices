package com.devcircus.java.microservices.mls.userservice.api.v1;

import com.devcircus.java.microservices.mls.userservice.user.User;
import com.devcircus.java.microservices.mls.userservice.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceV1 {

    private UserRepository userRepository;

    @Autowired
    public UserServiceV1(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findUserByUsername(username)).map(u -> u).orElse(null);
    }
}
