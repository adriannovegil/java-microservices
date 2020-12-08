package com.devcircus.java.microservices.mesp.springbootstarterdataevents.event;

import com.devcircus.java.microservices.mesp.springbootstarterdataevents.domain.Aggregate;
import org.springframework.hateoas.Link;

import java.io.Serializable;

public interface EventService<T extends Event, ID extends Serializable> {

    <E extends Aggregate, S extends T> S send(S event, Link... links);

    <S extends T> Boolean sendAsync(S event, Link... links);

    <S extends T> S save(S event);

    <S extends T> S save(ID id, S event);

    <EID extends ID> T findOne(EID id);

    <E extends Events> E find(ID entityId);
}
