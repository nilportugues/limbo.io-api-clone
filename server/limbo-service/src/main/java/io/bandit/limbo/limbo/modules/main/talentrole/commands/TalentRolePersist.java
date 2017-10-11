package io.bandit.limbo.limbo.modules.main.talentrole.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.modules.main.talentrole.event.TalentRoleCreated;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.TalentRoleRepository;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

public class TalentRolePersist {

    public static class Command implements ICommand {
        private TalentRole talentRole;

        public Command(final TalentRole talentRole) {
            this.talentRole = talentRole;
        }

        public TalentRole getTalentRole() {
            return talentRole;
        }
    }

    @Named("TalentRolePersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final TalentRolePersisterService persister;

        @Inject
        public CommandHandler(final TalentRolePersisterService service) {
            this.persister = service;
        }

        public CompletableFuture<TalentRole> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return persister.persist(command.getTalentRole());
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
