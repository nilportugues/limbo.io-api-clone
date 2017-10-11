package io.bandit.limbo.limbo.modules.main.joboffer.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.joboffer.event.JobOfferCreated;
import io.bandit.limbo.limbo.modules.main.joboffer.event.JobOfferDeleted;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.JobOfferRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class JobOfferTalentDeleteMany {

    public static class Command implements ICommand {
        private Iterable<String> jobOfferIds = new ArrayList<>();

        public Command(final Iterable<String> jobOfferIds) {

            if (Optional.ofNullable(jobOfferIds).isPresent()) {
                this.jobOfferIds = jobOfferIds;
            }
        }

        public Iterable<String> getJobOfferIds() {
            return jobOfferIds;
        }
    }

    @Named("JobOfferTalentDeleteMany.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final JobOfferRepository jobOfferRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final JobOfferRepository jobOfferRepository, final EventBus eventBus) {
            this.jobOfferRepository = jobOfferRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the current Talent value for a given set of JobOffers.
         */
        public CompletableFuture<List<JobOffer>> handle(final Command command) {
        //public Mono<List<JobOffer>> deleteTalents(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final List<JobOffer> list = new ArrayList<>();

                command.getJobOfferIds().forEach(jobOfferId -> {
                    final JobOffer jobOffer = jobOfferRepository.findOneWithTalent(jobOfferId);
                    jobOfferRepository.delete(jobOfferId);

                    final List<DomainEvent> events = jobOffer.pullEvents();
                    events.forEach(v -> eventBus.dispatch(v));

                    list.add(jobOffer);
                });

                return list;
            });
        }
    }
}
