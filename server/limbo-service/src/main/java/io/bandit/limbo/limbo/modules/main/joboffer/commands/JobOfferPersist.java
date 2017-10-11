package io.bandit.limbo.limbo.modules.main.joboffer.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.joboffer.event.JobOfferCreated;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.JobOfferRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

public class JobOfferPersist {

    public static class Command implements ICommand {
        private JobOffer jobOffer;

        public Command(final JobOffer jobOffer) {
            this.jobOffer = jobOffer;
        }

        public JobOffer getJobOffer() {
            return jobOffer;
        }
    }

    @Named("JobOfferPersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final JobOfferPersisterService persister;

        @Inject
        public CommandHandler(final JobOfferPersisterService service) {
            this.persister = service;
        }

        public CompletableFuture<JobOffer> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return persister.persist(command.getJobOffer());
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
