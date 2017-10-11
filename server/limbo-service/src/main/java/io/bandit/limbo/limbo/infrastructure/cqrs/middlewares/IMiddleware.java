package io.bandit.limbo.limbo.infrastructure.cqrs.middlewares;

import java.util.concurrent.CompletableFuture;

public interface IMiddleware {
    CompletableFuture execute(final Object object);
}