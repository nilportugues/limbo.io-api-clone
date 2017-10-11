package io.bandit.limbo.limbo.modules.main.notableprojects.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.notableprojects.event.NotableProjectsCreated;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.NotableProjectsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

public class NotableProjectsPersist {

    public static class Command implements ICommand {
        private NotableProjects notableProjects;

        public Command(final NotableProjects notableProjects) {
            this.notableProjects = notableProjects;
        }

        public NotableProjects getNotableProjects() {
            return notableProjects;
        }
    }

    @Named("NotableProjectsPersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final NotableProjectsPersisterService persister;

        @Inject
        public CommandHandler(final NotableProjectsPersisterService service) {
            this.persister = service;
        }

        public CompletableFuture<NotableProjects> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return persister.persist(command.getNotableProjects());
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
