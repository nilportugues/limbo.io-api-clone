package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.companytraits.event.CompanyTraitsDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentCompanyTraitsDeleteMany {

    public static class Command implements ICommand {
        private String talentId;
        private Iterable<String> companyTraitsIds = new ArrayList<>();

        public Command(final String talentId, final Iterable<String> companyTraitsIds) {
            this.talentId = talentId;

            if (Optional.ofNullable(companyTraitsIds).isPresent()) {
                this.companyTraitsIds = companyTraitsIds;
            }
        }

        public String getTalentId() {
            return talentId;
        }

        public Iterable<String> getCompanyTraitsIds() {
            return companyTraitsIds;
        }
    }

    @Named("TalentCompanyTraitsDeleteMany.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final TalentRepository talentRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final TalentRepository talentRepository, final EventBus eventBus) {
            this.talentRepository = talentRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete list of CompanyTraits for a given Talents.
         */
        public CompletableFuture<Talent> handle(final Command command) {
        //public Mono<Talent> deleteAllCompanyTraits(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final List<DomainEvent> domainEvents = new ArrayList<>();
                final List<String> ids = new ArrayList<>();

                final String id = command.getTalentId();
                final Talent talent = talentRepository.findOneWithCompanyTraits(id);

                final Iterable<String> companyTraitsIds = command.getCompanyTraitsIds();
                companyTraitsIds.forEach(ids::add);

                talent.getCompanyTraits().forEach(v -> {
                    if (ids.contains(v.getId())) {
                        domainEvents.add(new CompanyTraitsDeleted(v));
                    }
                });

                talentRepository.deleteAllCompanyTraits(id, companyTraitsIds);
                domainEvents.forEach(eventBus::dispatch);

                return talent;
            });
        }
    }
}
