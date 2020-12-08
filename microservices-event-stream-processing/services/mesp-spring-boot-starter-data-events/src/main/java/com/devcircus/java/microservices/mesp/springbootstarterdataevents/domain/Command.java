package com.devcircus.java.microservices.mesp.springbootstarterdataevents.domain;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Command {

    String description() default "";

    @Required
    String method() default "";

    @Required
    Class controller();
}
