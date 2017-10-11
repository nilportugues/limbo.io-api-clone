package io.bandit.limbo.limbo.modules.main.companytraits.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.companytraits.event.CompanyTraitsCreated;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.CompanyTraitsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

public class CompanyTraitsPersist {

    public static class Command implements ICommand {
        private CompanyTraits companyTraits;

        public Command(final CompanyTraits companyTraits) {
            this.companyTraits = companyTraits;
        }

        public CompanyTraits getCompanyTraits() {
            return companyTraits;
        }
    }

    @Named("CompanyTraitsPersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final CompanyTraitsPersisterService persister;

        @Inject
        public CommandHandler(final CompanyTraitsPersisterService service) {
            this.persister = service;
        }

        public CompletableFuture<CompanyTraits> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return persister.persist(command.getCompanyTraits());
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
