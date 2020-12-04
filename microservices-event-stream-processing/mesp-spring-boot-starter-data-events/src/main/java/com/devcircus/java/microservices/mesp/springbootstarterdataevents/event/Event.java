package com.devcircus.java.microservices.mesp.springbootstarterdataevents.event;

import demo.domain.Aggregate;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.EvoInflectorRelProvider;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Event<T extends Aggregate, E, ID extends Serializable> extends ResourceSupport {

    public Event() {
    }

    public abstract ID getEventId();

    public abstract void setEventId(ID eventId);

    public abstract E getType();

    public abstract void setType(E type);

    public abstract T getEntity();

    public abstract void setEntity(T entity);

    public abstract Long getCreatedAt();

    public abstract void setCreatedAt(Long createdAt);

    public abstract Long getLastModified();

    public abstract void setLastModified(Long lastModified);

    @Override
    @SuppressWarnings("unchecked")
    public List<Link> getLinks() {
        List<Link> links = super.getLinks().stream().collect(Collectors.toList());
        links.add(getId());
        Class<T> clazz = (Class<T>) ((ParameterizedTypeImpl) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        links.add(getEntity().getId().withRel(new EvoInflectorRelProvider().getItemResourceRelFor(clazz)));
        return links;
    }

    @Override
    public String toString() {
        return String.format("links: %s", getLinks().toString());
    }
}
