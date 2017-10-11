package io.bandit.limbo.limbo.modules.main.joboffer.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.joboffer.event.JobOfferCreated;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.JobOfferRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentCreated;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class JobOfferTalentPersist {

    public static class Command implements ICommand {
        private String jobOfferId;
        private Talent talent;

        public Command(final String jobOfferId, final Talent talent) {
            this.jobOfferId = jobOfferId;
            this.talent = talent;
        }

        public String getJobOfferId() {
             return jobOfferId;
        }

        public Talent getTalent() {
            return talent;
        }
    }

    @Named("JobOfferTalentPersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final JobOfferRepository jobOfferRepository;
        private final JobOfferPersisterService persister;

        @Inject
        public CommandHandler(final JobOfferRepository jobOfferRepository,
                              final JobOfferPersisterService persister) {

            this.jobOfferRepository = jobOfferRepository;
            this.persister = persister;
        }

        /**
         * Persist the current Talent value for a given JobOffer.
         */
        public CompletableFuture<JobOffer> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    final JobOffer jobOffer = jobOfferRepository.findOne(command.getJobOfferId());
                    if (null == jobOffer){
                        return null;
                    }

                    jobOffer.setTalent(command.getTalent());

                    return persister.persist(jobOffer);
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
