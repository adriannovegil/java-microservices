package com.devcircus.java.microservices.mesp.springbootstarterdataevents.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;

@NoRepositoryBean
public interface EventRepository<E extends Event, ID extends Serializable> extends PagingAndSortingRepository<E, ID> {

    Page<E> findEventsByEntityId(@Param("entityId") ID entityId, Pageable pageable);
}
