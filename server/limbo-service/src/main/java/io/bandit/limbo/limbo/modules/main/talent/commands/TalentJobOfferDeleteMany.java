package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.joboffer.event.JobOfferDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentJobOfferDeleteMany {

    public static class Command implements ICommand {
        private String talentId;
        private Iterable<String> jobOfferIds = new ArrayList<>();

        public Command(final String talentId, final Iterable<String> jobOfferIds) {
            this.talentId = talentId;

            if (Optional.ofNullable(jobOfferIds).isPresent()) {
                this.jobOfferIds = jobOfferIds;
            }
        }

        public String getTalentId() {
            return talentId;
        }

        public Iterable<String> getJobOfferIds() {
            return jobOfferIds;
        }
    }

    @Named("TalentJobOfferDeleteMany.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final TalentRepository talentRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final TalentRepository talentRepository, final EventBus eventBus) {
            this.talentRepository = talentRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete list of JobOffer for a given Talents.
         */
        public CompletableFuture<Talent> handle(final Command command) {
        //public Mono<Talent> deleteAllJobOffers(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final List<DomainEvent> domainEvents = new ArrayList<>();
                final List<String> ids = new ArrayList<>();

                final String id = command.getTalentId();
                final Talent talent = talentRepository.findOneWithJobOffers(id);

                final Iterable<String> jobOfferIds = command.getJobOfferIds();
                jobOfferIds.forEach(ids::add);

                talent.getJobOffers().forEach(v -> {
                    if (ids.contains(v.getId())) {
                        domainEvents.add(new JobOfferDeleted(v));
                    }
                });

                talentRepository.deleteAllJobOffers(id, jobOfferIds);
                domainEvents.forEach(eventBus::dispatch);

                return talent;
            });
        }
    }
}
