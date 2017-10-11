package io.bandit.limbo.limbo.modules.main.talentexperience.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.main.talentexperience.event.TalentExperienceCreated;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.TalentExperienceRepository;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

public class TalentExperiencePersist {

    public static class Command implements ICommand {
        private TalentExperience talentExperience;

        public Command(final TalentExperience talentExperience) {
            this.talentExperience = talentExperience;
        }

        public TalentExperience getTalentExperience() {
            return talentExperience;
        }
    }

    @Named("TalentExperiencePersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final TalentExperiencePersisterService persister;

        @Inject
        public CommandHandler(final TalentExperiencePersisterService service) {
            this.persister = service;
        }

        public CompletableFuture<TalentExperience> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return persister.persist(command.getTalentExperience());
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
