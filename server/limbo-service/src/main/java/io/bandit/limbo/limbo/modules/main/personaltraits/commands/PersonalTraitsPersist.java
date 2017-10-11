package io.bandit.limbo.limbo.modules.main.personaltraits.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.personaltraits.event.PersonalTraitsCreated;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.PersonalTraitsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

public class PersonalTraitsPersist {

    public static class Command implements ICommand {
        private PersonalTraits personalTraits;

        public Command(final PersonalTraits personalTraits) {
            this.personalTraits = personalTraits;
        }

        public PersonalTraits getPersonalTraits() {
            return personalTraits;
        }
    }

    @Named("PersonalTraitsPersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final PersonalTraitsPersisterService persister;

        @Inject
        public CommandHandler(final PersonalTraitsPersisterService service) {
            this.persister = service;
        }

        public CompletableFuture<PersonalTraits> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return persister.persist(command.getPersonalTraits());
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
