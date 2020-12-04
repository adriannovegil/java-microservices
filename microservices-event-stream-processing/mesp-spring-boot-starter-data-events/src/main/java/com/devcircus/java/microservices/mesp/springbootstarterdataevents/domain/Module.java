package com.devcircus.java.microservices.mesp.springbootstarterdataevents.domain;

import demo.event.EventService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public abstract class Module<T extends Aggregate> implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Module.applicationContext = applicationContext;
    }

    public abstract Service<? extends Aggregate, ?> getDefaultService();

    public abstract EventService<?, ?> getDefaultEventService();
}
