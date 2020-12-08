package com.devcircus.java.microservices.mmr.usersservice.domain.rels;

import com.devcircus.java.microservices.mmr.usersservice.domain.nodes.Content;
import com.devcircus.java.microservices.mmr.usersservice.domain.nodes.Event;
import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "ON")
public class On {

    @GraphId
    Long id;
    @StartNode
    Event event;
    @EndNode
    Content content;

    public On() {
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
