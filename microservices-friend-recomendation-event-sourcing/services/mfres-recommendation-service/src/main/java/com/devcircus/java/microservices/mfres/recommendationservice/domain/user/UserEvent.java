package com.devcircus.java.microservices.mfres.recommendationservice.domain.user;

import io.example.domain.user.entity.User;

public class UserEvent {

    private User subject;
    private UserEventType eventType;

    public UserEvent() {
    }

    public UserEvent(User subject, UserEventType eventType) {
        this.subject = subject;
        this.eventType = eventType;
    }

    public User getSubject() {
        return subject;
    }

    public void setSubject(User subject) {
        this.subject = subject;
    }

    public UserEventType getEventType() {
        return eventType;
    }

    public void setEventType(UserEventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "UserEvent{"
                + "subject=" + subject
                + ", eventType=" + eventType
                + '}';
    }
}
