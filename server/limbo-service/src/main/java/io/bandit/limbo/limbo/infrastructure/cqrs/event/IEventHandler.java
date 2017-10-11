package io.bandit.limbo.limbo.infrastructure.cqrs.event;

import java.util.concurrent.CompletableFuture;

public interface IEventHandler<E extends IEvent> {

    CompletableFuture<Void> handle(final E event);
}
