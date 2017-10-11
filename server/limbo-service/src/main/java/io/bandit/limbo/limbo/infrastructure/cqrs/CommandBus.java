package io.bandit.limbo.limbo.infrastructure.cqrs;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.*;
import io.bandit.limbo.limbo.infrastructure.cqrs.middlewares.IMiddleware;
import io.bandit.limbo.limbo.infrastructure.cqrs.resolvers.ResolverStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CommandBus implements ICommandBus {

    private static final Logger log = LoggerFactory.getLogger(CommandBus.class);
    private static final String EXCEPTION_MSG = "Could not find command handler for class: ";

    private final CommandRegistry registry;
    private final ResolverStrategy resolver;
    private final List<IMiddleware> middlewares = new LinkedList<>();

    public CommandBus(final CommandRegistry registry, final ResolverStrategy resolver) {
        this.registry = registry;
        this.resolver = resolver;
    }

    public void addMiddleware(final IMiddleware middleware) {
        middlewares.add(middleware);
    }

    @Override
    @SuppressWarnings("unchecked")
    public CompletableFuture execute(final ICommand command) {

        if (middlewares.size() > 0) {
            return applyMiddlewares(command).thenCompose(s -> getLast().execute(command));
        }

        return getLast().execute(command);
    }

    /**
     * Chain all middlewares using futures.
     */
    private CompletableFuture applyMiddlewares(final ICommand command) {
        final ArrayList<IMiddleware> middlewares = new ArrayList<>(this.middlewares);
        final IMiddleware middleware = middlewares.get(0);
        middlewares.remove(0);

        CompletableFuture future = middleware.execute(command);
        for (final IMiddleware m: middlewares) {
            future = future.thenCompose(s -> m.execute(command));
        }
        return future;
    }

    @SuppressWarnings("unchecked")
    private IMiddleware getLast() {
        return middleware -> {
            final String commandName = resolver.get(middleware);
            final Optional optional = Optional.ofNullable(registry.get((ICommand) middleware));

            if (!optional.isPresent()) {
                throw new RuntimeException(EXCEPTION_MSG + commandName);
            }

            final ICommandHandler commandHandler = (ICommandHandler) optional.get();
            return commandHandler.handle((ICommand) middleware);
        };
    }
}
