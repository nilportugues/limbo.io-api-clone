package io.bandit.limbo.limbo.infrastructure.cqrs.command;

import io.bandit.limbo.limbo.infrastructure.cqrs.middlewares.IMiddleware;

import java.util.concurrent.CompletableFuture;

public interface ICommandBus {

    CompletableFuture<?> execute(final ICommand command);
    void addMiddleware(final IMiddleware middleware);
}
