package com.devcircus.java.microservices.mesp.springbootstarterdataevents.domain;

import com.devcircus.java.microservices.mesp.springbootstarterdataevents.event.Event;
import com.devcircus.java.microservices.mesp.springbootstarterdataevents.event.EventService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.hateoas.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public abstract class Aggregate<E extends Event, ID extends Serializable> extends ResourceSupport implements
        Entity<Link> {

    @JsonProperty("id")
    public abstract ID getIdentity();

    private final ApplicationContext applicationContext = Optional.ofNullable(Module.getApplicationContext())
            .orElse(null);

    @SuppressWarnings("unchecked")
    @JsonIgnore
    protected <T extends Action<A>, A extends Aggregate> T getAction(
            Class<T> actionType) throws IllegalArgumentException {
        Module provider = getModule();
        Service service = provider.getDefaultService();
        return (T) service.getAction(actionType);
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    public <T extends Module<A>, A extends Aggregate<E, ID>> T getModule() throws IllegalArgumentException {
        return getModule((Class<T>) ResolvableType
                .forClassWithGenerics(Module.class, ResolvableType.forInstance(this))
                .getRawClass());
    }

    @JsonIgnore
    public <T extends Module<A>, A extends Aggregate<E, ID>> T getModule(Class<T> providerType) throws
            IllegalArgumentException {
        Assert.notNull(applicationContext, "The application context is unavailable");
        T provider = applicationContext.getBean(providerType);
        Assert.notNull(provider, "The requested provider is not registered in the application context");
        return (T) provider;
    }

    @JsonIgnore
    public abstract List<E> getEvents();

    public E sendEvent(E event, Link... links) {
        return getEventService().send(appendEvent(event), links);
    }

    public boolean sendAsyncEvent(E event, Link... links) {
        return getEventService().sendAsync(appendEvent(event), links);
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public E appendEvent(E event) {
        event.setEntity(this);
        getEventService().save(event);
        getEvents().add(event);
        getEntityService().update(this);
        return event;
    }

    @Override
    public List<Link> getLinks() {
        List<Link> links = super.getLinks()
                .stream()
                .collect(Collectors.toList());

        if (!super.hasLink("self")) {
            links.add(getId());
        }

        return links;
    }

    @JsonIgnore
    public CommandResources getCommands() {
        CommandResources commandResources = new CommandResources();

        // Get command annotations on the aggregate
        List<Command> commands = Arrays.stream(this.getClass()
                .getMethods())
                .filter(a -> a.isAnnotationPresent(Command.class))
                .map(a -> a.getAnnotation(Command.class))
                .collect(Collectors.toList());

        // Compile the collection of command links
        List<Link> commandLinks = commands.stream()
                .map(a -> Arrays.stream(ReflectionUtils.getAllDeclaredMethods(a.controller()))
                .filter(m -> m.getName()
                .equalsIgnoreCase(a.method()))
                .findFirst()
                .orElseGet(null))
                .map(m -> {
                    String uri = linkTo(m, getIdentity()).withRel(m.getName())
                            .getHref();

                    return new Link(new UriTemplate(uri, new TemplateVariables(Arrays.stream(m.getParameters())
                            .filter(p -> p.isAnnotationPresent(RequestParam.class))
                            .map(p -> new TemplateVariable(p.getAnnotation(RequestParam.class)
                            .value(), TemplateVariable.VariableType.REQUEST_PARAM))
                            .toArray(TemplateVariable[]::new))), m.getName());
                })
                .collect(Collectors.toList());

        commandResources.add(commandLinks);

        return commandResources;
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    protected Service<Aggregate<E, ID>, ID> getEntityService() {
        return (Service<Aggregate<E, ID>, ID>) getModule().getDefaultService();
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    protected EventService<E, ID> getEventService() {
        return (EventService<E, ID>) getModule().getDefaultEventService();
    }

    public static class CommandResources extends ResourceSupport {
    }

}
