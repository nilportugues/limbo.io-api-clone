package io.bandit.limbo.limbo.infrastructure.cqrs.command;

import java.util.concurrent.CompletableFuture;

public interface ICommandHandler<C extends ICommand> {

    /**
     * Handles the command.
     * @param command command to handle
     * @return an CompletableFuture return value.
     */
    CompletableFuture handle(final C command);
}
