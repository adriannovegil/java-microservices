package com.devcircus.java.microservices.mesp.springbootstarterdataevents.domain;

import org.springframework.hateoas.Identifiable;

import java.io.Serializable;

public interface Entity<ID extends Serializable> extends Identifiable<ID> {
}
