package io.bandit.limbo.limbo.infrastructure.cqrs;

import io.bandit.limbo.limbo.infrastructure.cqrs.resolvers.ResolverStrategy;
import io.bandit.limbo.limbo.infrastructure.cqrs.event.EventRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEvent;
import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;

public class EventBus {

    private static final Logger log = LoggerFactory.getLogger(EventBus.class);
    private static final String LOG_MSG = "Handling event {} ...";
    private static final String EXCEPTION_MSG = "Could not find event handler for class: ";

    private final EventRegistry registry;
    private final ResolverStrategy resolver;

    public EventBus(final EventRegistry registry, final ResolverStrategy resolver) {
        this.registry = registry;
        this.resolver = resolver;
    }

    @SuppressWarnings("unchecked")
    public void dispatch(IEvent event) {
        final String eventName = resolver.get(event);

        try {
            final Optional optional = Optional.ofNullable(registry.get(eventName));

            if (!optional.isPresent()) {
                throw new RuntimeException(EXCEPTION_MSG + eventName);
            }

            final List<IEventHandler> eventHandlers = (List<IEventHandler>) optional.get();

            Flux.fromIterable(eventHandlers)
                    .doOnNext(eventHandler -> eventHandler.handle(event))
                    .publishOn(Schedulers.parallel())
                    .subscribe(eventHandler -> log.debug(LOG_MSG, eventHandler.getClass().toString()));
        } catch (Throwable t) {
            t.printStackTrace();
            log.error("[EventBus] Could not associated handler for: {}", eventName);
        }
    }
}
