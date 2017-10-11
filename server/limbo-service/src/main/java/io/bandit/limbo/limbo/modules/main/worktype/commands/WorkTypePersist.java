package io.bandit.limbo.limbo.modules.main.worktype.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.main.worktype.event.WorkTypeCreated;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.WorkTypeRepository;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

public class WorkTypePersist {

    public static class Command implements ICommand {
        private WorkType workType;

        public Command(final WorkType workType) {
            this.workType = workType;
        }

        public WorkType getWorkType() {
            return workType;
        }
    }

    @Named("WorkTypePersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final WorkTypePersisterService persister;

        @Inject
        public CommandHandler(final WorkTypePersisterService service) {
            this.persister = service;
        }

        public CompletableFuture<WorkType> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return persister.persist(command.getWorkType());
                } catch (Throwable throwable) {
                    throwAsyncException(throwable);
                    return null;
                }
            });
        }

        private void throwAsyncException(final Throwable throwable) {
            final Thread thread = Thread.currentThread();
            thread.getUncaughtExceptionHandler().uncaughtException(thread, throwable);
        }
    }
}
