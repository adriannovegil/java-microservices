package com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.tweet;

import org.kbastani.user.User;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "TWEETED")
public class Tweeted {

    @Id
    @GeneratedValue
    private Long id;
    @StartNode
    private User user;
    @EndNode
    private Tweet tweet;

    public Tweeted() {
    }

    public Tweeted(User user, Tweet tweet) {
        this.user = user;
        this.tweet = tweet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    @Override
    public String toString() {
        return "Tweeted{"
                + "id=" + id
                + ", user=" + user
                + ", tweet=" + tweet
                + '}';
    }
}
