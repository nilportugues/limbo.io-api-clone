package io.bandit.limbo.limbo.infrastructure.cqrs.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.di.IBeanProvider;
import io.bandit.limbo.limbo.infrastructure.cqrs.resolvers.ResolverStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventRegistry {

    private final Map<String, List<String>> events;
    private static final Logger log = LoggerFactory.getLogger(EventRegistry.class);
    private final IBeanProvider provider;
    private final ResolverStrategy resolver;

    public EventRegistry(final IBeanProvider provider, final ResolverStrategy resolver) {
        this.events = new HashMap<>();
        this.provider = provider;
        this.resolver = resolver;
    }

    public void register(final Class<? extends IEvent> event, final String handler) {
        final String eventName = resolver.get(event);

        if (!events.containsKey(eventName)) {
            events.put(eventName, new ArrayList<>());
        }

        events.get(eventName).add(handler);
        log.info("Events registered for "+ eventName);
    }

    public List<IEventHandler> get(final String eventName) {
        final List<String> eventHandlerNames = events.get(eventName);
        final List<IEventHandler> instances = new ArrayList<>();
        eventHandlerNames.forEach(handlerName -> instances.add((IEventHandler) provider.get(handlerName)));

        return instances;
    }
}
