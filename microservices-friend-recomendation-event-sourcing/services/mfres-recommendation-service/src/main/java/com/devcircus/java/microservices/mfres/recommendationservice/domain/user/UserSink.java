package com.devcircus.java.microservices.mfres.recommendationservice.domain.user;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface UserSink {

    String INPUT = "user";

    @Input(UserSink.INPUT)
    SubscribableChannel user();
}
