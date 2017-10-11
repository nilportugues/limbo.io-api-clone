package io.bandit.limbo.limbo.infrastructure.cqrs.query;

import io.bandit.limbo.limbo.infrastructure.cqrs.middlewares.IMiddleware;

import java.util.concurrent.CompletableFuture;

public interface IQueryBus {

    CompletableFuture<?> dispatch(final IQuery query);
    void addMiddleware(final IMiddleware middleware);
}
