package io.bandit.limbo.limbo.modules.cqrs.middleware;

import io.bandit.limbo.limbo.infrastructure.cqrs.middlewares.IMiddleware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class NullMiddleware implements IMiddleware {

    private static Logger logger = LoggerFactory.getLogger(NullMiddleware.class);

    @Override
    public CompletableFuture execute(Object object) {
        return CompletableFuture.runAsync(() -> {
            logger.debug("{} started doing just nothing... ", this.getClass().getSimpleName());
        })
        .whenCompleteAsync((aVoid, throwable) -> {
            logger.debug("{} ended doing just nothing... ", this.getClass().getSimpleName());
        });
    }
}
