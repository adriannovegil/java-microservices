package com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.text;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class TextEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    /**
     * 
     */
    public TextEntity() {
    }

    /**
     * 
     * @param name 
     */
    public TextEntity(String name) {
        this.name = name;
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
    @Override
    public String toString() {
        return "TextEntity{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
