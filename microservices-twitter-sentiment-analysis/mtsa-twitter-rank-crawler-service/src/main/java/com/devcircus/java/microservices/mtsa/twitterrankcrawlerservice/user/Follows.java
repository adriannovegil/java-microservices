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

    public Follows(User userA, User userB) {
        this.userA = userA;
        this.userB = userB;
    }

    public Follows() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserA() {
        return userA;
    }

    public void setUserA(User userA) {
        this.userA = userA;
    }

    public User getUserB() {
        return userB;
    }

    public void setUserB(User userB) {
        this.userB = userB;
    }

    @Override
    public String toString() {
        return "Follows{"
                + "id=" + id
                + ", userA=" + userA
                + ", userB=" + userB
                + '}';
    }
}
