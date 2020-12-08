package com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.user;

import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.ArrayList;
import java.util.List;

@QueryResult
public class SentimentResult {

    private Long userProfileId;
    private List<Double> sentiment = new ArrayList<>();

    /**
     * 
     */
    public SentimentResult() {
    }

    /**
     * 
     * @return 
     */
    public Long getUserProfileId() {
        return userProfileId;
    }

    /**
     * 
     * @param userProfileId 
     */
    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    /**
     * 
     * @return 
     */
    public List<Double> getSentiment() {
        return sentiment;
    }

    /**
     * 
     * @param sentiment 
     */
    public void setSentiment(List<Double> sentiment) {
        this.sentiment = sentiment;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "SentimentResult{"
                + "userProfileId=" + userProfileId
                + ", sentiment=" + sentiment
                + '}';
    }
}
