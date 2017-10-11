package io.bandit.limbo.limbo.modules.main.talentprofile.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.main.talentprofile.event.TalentProfileCreated;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.TalentProfileRepository;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

public class TalentProfilePersist {

    public static class Command implements ICommand {
        private TalentProfile talentProfile;

        public Command(final TalentProfile talentProfile) {
            this.talentProfile = talentProfile;
        }

        public TalentProfile getTalentProfile() {
            return talentProfile;
        }
    }

    @Named("TalentProfilePersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final TalentProfilePersisterService persister;

        @Inject
        public CommandHandler(final TalentProfilePersisterService service) {
            this.persister = service;
        }

        public CompletableFuture<TalentProfile> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return persister.persist(command.getTalentProfile());
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
