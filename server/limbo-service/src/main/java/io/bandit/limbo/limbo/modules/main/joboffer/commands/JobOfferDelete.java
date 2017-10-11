package io.bandit.limbo.limbo.modules.main.joboffer.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.joboffer.event.JobOfferDeleted;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.JobOfferRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

public class JobOfferDelete {

    public static class Command implements ICommand {
         private String jobOfferId;

         public Command(final String jobOfferId) {
             this.jobOfferId = jobOfferId;
         }

         public String getJobOfferId() {
             return jobOfferId;
        }
    }

    @Named("JobOfferDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final JobOfferRepository jobOfferRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final JobOfferRepository jobOfferRepository, final EventBus eventBus) {
            this.jobOfferRepository = jobOfferRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the JobOffer by id.
         */
        public CompletableFuture<JobOffer> handle(final Command command) {
        //public Mono<JobOffer> delete(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                final JobOffer jobOffer = jobOfferRepository.findOne(command.getJobOfferId());

                if (null != jobOffer) {
                    this.removeDomainRelationships(jobOffer);
                    this.removePersistenceRelationships(jobOffer);
                    this.raiseDeletedEvent(jobOffer);
                }

                return jobOffer;
            });
        }

        private void removeDomainRelationships(final JobOffer jobOffer) {
            jobOffer.setTalent(null);
        }

        private void removePersistenceRelationships(final JobOffer jobOffer) {
            jobOfferRepository.save(jobOffer);
            jobOfferRepository.delete(jobOffer.getId());
        }

        private void raiseDeletedEvent(final JobOffer jobOffer) {
            eventBus.dispatch(new JobOfferDeleted(jobOffer));
        }
    }

}
