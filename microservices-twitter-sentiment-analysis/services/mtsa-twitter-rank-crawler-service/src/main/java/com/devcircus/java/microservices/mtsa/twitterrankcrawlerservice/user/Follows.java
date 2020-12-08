package com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.user;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "FOLLOWS")
public class Follows {

    @Id
    @GeneratedValue
    private Long id;
    @StartNode
    private User userA;
    @EndNode
    private User userB;

    /**
     * 
     * @param userA
     * @param userB 
     */
    public Follows(User userA, User userB) {
        this.userA = userA;
        this.userB = userB;
    }

    /**
     * 
     */
    public Follows() {
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
    public User getUserA() {
        return userA;
    }

    /**
     * 
     * @param userA 
     */
    public void setUserA(User userA) {
        this.userA = userA;
    }

    /**
     * 
     * @return 
     */
    public User getUserB() {
        return userB;
    }

    /**
     * 
     * @param userB 
     */
    public void setUserB(User userB) {
        this.userB = userB;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Follows{"
                + "id=" + id
                + ", userA=" + userA
                + ", userB=" + userB
                + '}';
    }
}
