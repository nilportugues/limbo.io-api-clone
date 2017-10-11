package io.bandit.limbo.limbo.infrastructure.cqrs;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.*;
import io.bandit.limbo.limbo.infrastructure.cqrs.middlewares.IMiddleware;
import io.bandit.limbo.limbo.infrastructure.cqrs.resolvers.ResolverStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class QueryBus implements IQueryBus {

    private static final Logger log = LoggerFactory.getLogger(QueryBus.class);
    private static final String EXCEPTION_MSG = "Could not find query handler for class: ";

    private final QueryRegistry registry;
    private final ResolverStrategy resolver;
    private final List<IMiddleware> middlewares = new LinkedList<>();

    public QueryBus(final QueryRegistry registry, final ResolverStrategy resolver) {
        this.registry = registry;
        this.resolver = resolver;
    }

    public void addMiddleware(final IMiddleware middleware) {
        middlewares.add(middleware);
    }

    @Override
    @SuppressWarnings("unchecked")
    public CompletableFuture dispatch(final IQuery query) {

        if (middlewares.size() > 0) {
            return applyMiddlewares(query).thenCompose(s -> getLast().execute(query));
        }

        return getLast().execute(query);
    }

    /**
     * Chain all middlewares using futures.
     */
    private CompletableFuture applyMiddlewares(final IQuery query) {
        final ArrayList<IMiddleware> middlewares = new ArrayList<>(this.middlewares);
        final IMiddleware middleware = middlewares.get(0);
        middlewares.remove(0);

        CompletableFuture future = middleware.execute(query);
        for (final IMiddleware m: middlewares) {
            future = future.thenCompose(s -> m.execute(query));
        }
        return future;
    }

    @SuppressWarnings("unchecked")
    private IMiddleware getLast() {
        return middleware -> {
            final String queryName = resolver.get(middleware);
            final Optional optional = Optional.ofNullable(registry.get((IQuery) middleware));

            if (!optional.isPresent()) {
                throw new RuntimeException(EXCEPTION_MSG + queryName);
            }

            final IQueryHandler queryHandler = (IQueryHandler) optional.get();
            return queryHandler.handle((IQuery) middleware);
        };
    }
}
