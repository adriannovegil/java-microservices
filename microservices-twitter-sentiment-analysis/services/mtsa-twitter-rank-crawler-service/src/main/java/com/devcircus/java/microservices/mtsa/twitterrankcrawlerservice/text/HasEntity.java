package com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.text;

import com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.tweet.Tweet;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "HAS_ENTITY")
public class HasEntity {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Tweet tweet;
    @EndNode
    private TextEntity textEntity;
    private Double salience;
    private Double sentiment;
    private Double magnitude;

    /**
     * 
     */
    public HasEntity() {
    }

    /**
     * 
     * @param tweet
     * @param textEntity 
     */
    public HasEntity(Tweet tweet, TextEntity textEntity) {
        this.tweet = tweet;
        this.textEntity = textEntity;
    }

    /**
     * 
     * @param tweet
     * @param textEntity
     * @param salience 
     */
    public HasEntity(Tweet tweet, TextEntity textEntity, Double salience) {
        this.tweet = tweet;
        this.textEntity = textEntity;
        this.salience = salience;
    }

    /**
     * 
     * @param tweet
     * @param textEntity
     * @param salience
     * @param sentiment
     * @param magnitude 
     */
    public HasEntity(Tweet tweet, TextEntity textEntity, Double salience, Double sentiment, Double magnitude) {
        this.tweet = tweet;
        this.textEntity = textEntity;
        this.salience = salience;
        this.sentiment = sentiment;
        this.magnitude = magnitude;
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
    public TextEntity getTextEntity() {
        return textEntity;
    }

    /**
     * 
     * @param textEntity 
     */
    public void setTextEntity(TextEntity textEntity) {
        this.textEntity = textEntity;
    }

    /**
     * 
     * @return 
     */
    public Double getSalience() {
        return salience;
    }

    /**
     * 
     * @param salience 
     */
    public void setSalience(Double salience) {
        this.salience = salience;
    }

    /**
     * 
     * @return 
     */
    public Double getSentiment() {
        return sentiment;
    }

    /**
     * 
     * @param sentiment 
     */
    public void setSentiment(Double sentiment) {
        this.sentiment = sentiment;
    }

    /**
     * 
     * @return 
     */
    public Double getMagnitude() {
        return magnitude;
    }

    /**
     * 
     * @param magnitude 
     */
    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "HasEntity{"
                + "id=" + id
                + ", tweet=" + tweet
                + ", textEntity=" + textEntity
                + ", salience=" + salience
                + '}';
    }
}
