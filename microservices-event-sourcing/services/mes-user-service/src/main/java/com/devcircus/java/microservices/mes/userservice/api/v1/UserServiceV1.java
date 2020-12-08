package com.devcircus.java.microservices.mes.userservice.api.v1;

import com.devcircus.java.microservices.mes.userservice.user.User;
import com.devcircus.java.microservices.mes.userservice.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceV1 {

    private UserRepository userRepository;

    @Autowired
    public UserServiceV1(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
