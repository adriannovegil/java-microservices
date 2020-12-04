package com.devcircus.java.microservices.mesp.springbootstarterdataevents.domain;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

@Component
public abstract class Action<A extends Aggregate> implements ApplicationContextAware {

    private final Logger log = Logger.getLogger(Action.class);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected Consumer<A> onSuccess(Map<String, Object> context) {
        return a -> {
        };
    }

    protected Consumer<A> onError(Map<String, Object> context) {
        return a -> {
        };
    }
}
