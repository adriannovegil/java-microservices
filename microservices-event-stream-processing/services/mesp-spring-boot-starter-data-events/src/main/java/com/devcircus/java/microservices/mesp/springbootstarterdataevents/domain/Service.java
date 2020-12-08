package com.devcircus.java.microservices.mesp.springbootstarterdataevents.domain;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;

@org.springframework.stereotype.Service
public abstract class Service<T extends Aggregate, ID extends Serializable> implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public abstract T get(ID id);

    public abstract T create(T entity);

    public abstract T update(T entity);

    public abstract boolean delete(ID id);

    @SuppressWarnings("unchecked")
    public <A extends Action<T>> A getAction(Class<? extends A> clazz) {
        return applicationContext.getBean(clazz);
    }
}
