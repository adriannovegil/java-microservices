package com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.tweet;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.Date;

@NodeEntity
public final class Tweet {

    @Id
    @GeneratedValue
    private Long id;
    private Long tweetId;
    private String text;
    private Boolean analyzed;
    private Long profileId;
    private Date createdDate;
    private Integer createdHour;
    private Integer createdDay;
    private Integer createdMonth;
    private Integer createdYear;
    private Long createdTimestamp;
    private Double sentiment;
    private Double magnitude;

    /**
     * 
     */
    public Tweet() {
    }

    /**
     * 
     * @param tweetId
     * @param text 
     */
    public Tweet(Long tweetId, String text) {
        this.tweetId = tweetId;
        this.text = text;
    }

    /**
     * 
     * @param tweetId
     * @param text
     * @param profileId 
     */
    public Tweet(Long tweetId, String text, Long profileId) {
        this.tweetId = tweetId;
        this.text = text;
        this.profileId = profileId;
    }

    /**
     * 
     * @param tweetId
     * @param text
     * @param profileId
     * @param createdDate 
     */
    public Tweet(Long tweetId, String text, Long profileId, Date createdDate) {
        this.tweetId = tweetId;
        this.text = text;
        this.profileId = profileId;
        this.createdDate = createdDate;
        // Index date/time
        this.setCreatedHour(createdDate.getHours());
        this.setCreatedDay(createdDate.getDate());
        this.setCreatedMonth(createdDate.getMonth());
        this.setCreatedYear(createdDate.getYear());
        this.setCreatedTimestamp(createdDate.getTime());
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
    public Long getTweetId() {
        return tweetId;
    }

    /**
     * 
     * @param tweetId 
     */
    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
    }

    /**
     * 
     * @return 
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text 
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 
     * @return 
     */
    public Boolean getAnalyzed() {
        return analyzed;
    }

    /**
     * 
     * @param analyzed 
     */
    public void setAnalyzed(Boolean analyzed) {
        this.analyzed = analyzed;
    }

    /**
     * 
     * @return 
     */
    public Long getProfileId() {
        return profileId;
    }

    /**
     * 
     * @param profileId 
     */
    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    /**
     * 
     * @return 
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * 
     * @param createdDate 
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * 
     * @return 
     */
    public Integer getCreatedDay() {
        return createdDay;
    }

    /**
     * 
     * @param createdDay 
     */
    public void setCreatedDay(Integer createdDay) {
        this.createdDay = createdDay;
    }

    /**
     * 
     * @return 
     */
    public Integer getCreatedMonth() {
        return createdMonth;
    }

    /**
     * 
     * @param createdMonth 
     */
    public void setCreatedMonth(Integer createdMonth) {
        this.createdMonth = createdMonth;
    }

    /**
     * 
     * @return 
     */
    public Integer getCreatedYear() {
        return createdYear;
    }

    /**
     * 
     * @param createdYear 
     */
    public void setCreatedYear(Integer createdYear) {
        this.createdYear = createdYear;
    }

    /**
     * 
     * @return 
     */
    public Long getCreatedTimestamp() {
        return createdTimestamp;
    }

    /**
     * 
     * @param createdTimestamp 
     */
    public void setCreatedTimestamp(Long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    /**
     * 
     * @return 
     */
    public Integer getCreatedHour() {
        return createdHour;
    }

    /**
     * 
     * @param createdHour 
     */
    public void setCreatedHour(Integer createdHour) {
        this.createdHour = createdHour;
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
        return "Tweet{"
                + "id=" + id
                + ", tweetId=" + tweetId
                + ", text='" + text + '\''
                + ", analyzed=" + analyzed
                + ", profileId=" + profileId
                + ", createdDate=" + createdDate
                + ", createdHour=" + createdHour
                + ", createdDay=" + createdDay
                + ", createdMonth=" + createdMonth
                + ", createdYear=" + createdYear
                + ", createdTimestamp=" + createdTimestamp
                + ", sentiment=" + sentiment
                + ", magnitude=" + magnitude
                + '}';
    }
}
