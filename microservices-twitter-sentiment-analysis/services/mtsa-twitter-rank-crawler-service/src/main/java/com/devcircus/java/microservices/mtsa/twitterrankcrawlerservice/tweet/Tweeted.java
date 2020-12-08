package com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.tweet;

import com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.user.User;
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

    /**
     * 
     */
    public Tweeted() {
    }

    /**
     * 
     * @param user
     * @param tweet 
     */
    public Tweeted(User user, Tweet tweet) {
        this.user = user;
        this.tweet = tweet;
    }

    /**
     * 
     * @return 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return 
     */
    public User getUser() {
        return user;
    }

    /**
     * 
     * @param user 
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 
     * @return 
     */
    public Tweet getTweet() {
        return tweet;
    }

    /**
     * 
     * @param tweet 
     */
    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Tweeted{"
                + "id=" + id
                + ", user=" + user
                + ", tweet=" + tweet
                + '}';
    }
}
