package com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.user;

import org.neo4j.ogm.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.annotation.Id;

@NodeEntity
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Index(unique = true)
    private Long profileId;

    @Relationship(type = "FOLLOWS", direction = "OUTGOING")
    private Set<User> follows = new HashSet<>();

    @Relationship(type = "FOLLOWS", direction = "INCOMING")
    private Set<User> followers = new HashSet<>();

    private String screenName;
    private String name;
    private String url;
    private String profileImageUrl;
    private String description;
    private String location;
    private Date createdDate;
    private Integer followerCount;
    private Integer followsCount;
    private Float pagerank;
    private Integer previousRank;
    private Integer currentRank;
    private Float lastPageRank;
    private Boolean imported;
    private Long discoveredTime;
    private Integer discoveredRank;
    private Long lastActivityScan;
    private Long lastImportedTweetId;
    private Double averageSentiment;
    private Double stdSentiment;
    private Double cumulativeSentiment;

    /**
     * 
     */
    public User() {
    }

    /**
     * 
     * @param id
     * @param profileId 
     */
    public User(Long id, Long profileId) {
        this.id = id;
        this.profileId = profileId;
    }

    /**
     * 
     * @param id
     * @param follows
     * @param followers 
     */
    public User(Long id, List<User> follows, List<User> followers) {
        this.profileId = id;
        this.follows.addAll(follows == null ? new HashSet<>() : follows);
        this.followers.addAll(followers == null ? new HashSet<>() : followers);
        this.follows = this.follows.stream().distinct().collect(Collectors.toSet());
    }

    /**
     * 
     * @param twitterProfile 
     */
    public User(twitter4j.User twitterProfile) {
        this.profileId = twitterProfile.getId();
        this.createdDate = twitterProfile.getCreatedAt();
        this.screenName = twitterProfile.getScreenName();
        this.name = twitterProfile.getName();
        this.url = twitterProfile.getURL();
        this.description = twitterProfile.getDescription();
        this.location = twitterProfile.getLocation();
        this.profileImageUrl = twitterProfile.getProfileImageURL();
        this.followerCount = twitterProfile.getFollowersCount();
        this.followsCount = twitterProfile.getFriendsCount();
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
    public String getScreenName() {
        return screenName;
    }

    /**
     * 
     * @param screenName 
     */
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    /**
     * 
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return 
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url 
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return 
     */
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    /**
     * 
     * @param profileImageUrl 
     */
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    /**
     * 
     * @return 
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description 
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return 
     */
    public String getLocation() {
        return location;
    }

    /**
     * 
     * @param location 
     */
    public void setLocation(String location) {
        this.location = location;
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
    public Set<User> getFollows() {
        return follows;
    }

    /**
     * 
     * @param follows 
     */
    public void setFollows(Set<User> follows) {
        this.follows = follows;
    }

    /**
     * 
     * @return 
     */
    public Set<User> getFollowers() {
        return followers;
    }

    /**
     * 
     * @param followers 
     */
    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    /**
     * 
     * @return 
     */
    public Integer getFollowerCount() {
        return followerCount;
    }

    /**
     * 
     * @param followerCount 
     */
    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    /**
     * 
     * @return 
     */
    public Integer getFollowsCount() {
        return followsCount;
    }

    /**
     * 
     * @param followsCount 
     */
    public void setFollowsCount(Integer followsCount) {
        this.followsCount = followsCount;
    }

    /**
     * 
     * @return 
     */
    public Float getPagerank() {
        return pagerank;
    }

    /**
     * 
     * @param pagerank 
     */
    public void setPagerank(Float pagerank) {
        this.pagerank = pagerank;
    }

    /**
     * 
     * @return 
     */
    public Integer getPreviousRank() {
        return previousRank;
    }

    /**
     * 
     * @param previousRank 
     */
    public void setPreviousRank(Integer previousRank) {
        this.previousRank = previousRank;
    }

    /**
     * 
     * @return 
     */
    public Integer getCurrentRank() {
        return currentRank;
    }

    /**
     * 
     * @param currentRank 
     */
    public void setCurrentRank(Integer currentRank) {
        this.currentRank = currentRank;
    }

    /**
     * 
     * @return 
     */
    public Float getLastPageRank() {
        return lastPageRank;
    }

    /**
     * 
     * @param lastPageRank 
     */
    public void setLastPageRank(Float lastPageRank) {
        this.lastPageRank = lastPageRank;
    }

    /**
     * 
     * @return 
     */
    public Boolean getImported() {
        return imported;
    }

    /**
     * 
     * @param imported 
     */
    public void setImported(Boolean imported) {
        this.imported = imported;
    }

    /**
     * 
     * @return 
     */
    public Long getDiscoveredTime() {
        return discoveredTime;
    }

    /**
     * 
     * @param discoveredTime 
     */
    public void setDiscoveredTime(Long discoveredTime) {
        this.discoveredTime = discoveredTime;
    }

    /**
     * 
     * @return 
     */
    public Integer getDiscoveredRank() {
        return discoveredRank;
    }

    /**
     * 
     * @param discoveredRank 
     */
    public void setDiscoveredRank(Integer discoveredRank) {
        this.discoveredRank = discoveredRank;
    }

    /**
     * 
     * @return 
     */
    public Long getLastActivityScan() {
        return lastActivityScan;
    }

    /**
     * 
     * @param lastActivityScan 
     */
    public void setLastActivityScan(Long lastActivityScan) {
        this.lastActivityScan = lastActivityScan;
    }

    /**
     * 
     * @return 
     */
    public Long getLastImportedTweetId() {
        return lastImportedTweetId;
    }

    /**
     * 
     * @param lastImportedTweetId 
     */
    public void setLastImportedTweetId(Long lastImportedTweetId) {
        this.lastImportedTweetId = lastImportedTweetId;
    }

    /**
     * 
     * @return 
     */
    public Double getAverageSentiment() {
        return averageSentiment;
    }

    /**
     * 
     * @param averageSentiment 
     */
    public void setAverageSentiment(Double averageSentiment) {
        this.averageSentiment = averageSentiment;
    }

    /**
     * 
     * @return 
     */
    public Double getStdSentiment() {
        return stdSentiment;
    }

    /**
     * 
     * @param stdSentiment 
     */
    public void setStdSentiment(Double stdSentiment) {
        this.stdSentiment = stdSentiment;
    }

    /**
     * 
     * @return 
     */
    public Double getCumulativeSentiment() {
        return cumulativeSentiment;
    }

    /**
     * 
     * @param cumulativeSentiment 
     */
    public void setCumulativeSentiment(Double cumulativeSentiment) {
        this.cumulativeSentiment = cumulativeSentiment;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", profileId=" + profileId
                + ", follows=" + follows
                + ", followers=" + followers
                + ", screenName='" + screenName + '\''
                + ", name='" + name + '\''
                + ", url='" + url + '\''
                + ", profileImageUrl='" + profileImageUrl + '\''
                + ", description='" + description + '\''
                + ", location='" + location + '\''
                + ", createdDate=" + createdDate
                + ", followerCount=" + followerCount
                + ", followsCount=" + followsCount
                + ", pagerank=" + pagerank
                + ", previousRank=" + previousRank
                + ", currentRank=" + currentRank
                + ", lastPageRank=" + lastPageRank
                + ", imported=" + imported
                + ", discoveredTime=" + discoveredTime
                + ", discoveredRank=" + discoveredRank
                + ", lastActivityScan=" + lastActivityScan
                + ", lastImportedTweetId=" + lastImportedTweetId
                + ", averageSentiment=" + averageSentiment
                + ", stdSentiment=" + stdSentiment
                + ", cumulativeSentiment=" + cumulativeSentiment
                + '}';
    }
}
