package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.joboffer.event.JobOfferCreated;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentJobOfferPersist {

    public static class Command implements ICommand {
        private String talentId;
        private JobOffer jobOffer;

        public Command(final String talentId,
                       final JobOffer jobOffer) {
            this.talentId = talentId;
            this.jobOffer = jobOffer;
        }

        public String getTalentId() {
            return talentId;
        }

        public JobOffer getJobOffer() {
            return jobOffer;
        }
    }

    @Named("TalentJobOfferPersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final TalentRepository talentRepository;
        private final TalentPersisterService persister;

        @Inject
        public CommandHandler(final TalentRepository talentRepository,
                              final TalentPersisterService persister) {

            this.talentRepository = talentRepository;
            this.persister = persister;
        }

        /**
         * Persist a JobOffer for a given Talents.
         */
        public CompletableFuture<JobOffer> handle(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                try {
                    final Talent talent = talentRepository.findOneWithJobOffers(command.getTalentId());
                    if (null == talent) {
                        return null;
                    }

                    final JobOffer jobOffer = command.getJobOffer();
                    talent.addJobOffer(jobOffer);
                    persister.persist(talent);

                    return jobOffer;
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
