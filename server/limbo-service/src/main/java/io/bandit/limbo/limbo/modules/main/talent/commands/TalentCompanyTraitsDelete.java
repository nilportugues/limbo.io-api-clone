package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.companytraits.event.CompanyTraitsDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentCompanyTraitsDelete {

    public static class Command implements ICommand {
        private String talentId;

        public Command(final String talentId) {
            this.talentId = talentId;
        }

        public String getTalentId() {
            return talentId;
        }
    }

    @Named("TalentCompanyTraitsDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final TalentRepository talentRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final TalentRepository talentRepository, final EventBus eventBus) {
            this.talentRepository = talentRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete all CompanyTraits for a given Talents.
         */
        public CompletableFuture<Talent> handle(final Command command) {
        //public Talent deleteAll(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final Talent talent = talentRepository.findOne(command.getTalentId());

                if (null != talent) {
                    final List<DomainEvent> domainEvents = new ArrayList<>();
                    findTalentWithCompanyTraits(talent);
                    createDeleteEvents(domainEvents, talent);
                    deleteAllCompanyTraits(talent);
                    raiseDeleteAllEvents(domainEvents);
                }
                return talent;
            });
        }

        private void createDeleteEvents(final List<DomainEvent> domainEvents, final Talent talent) {
            talent.getCompanyTraits().forEach(v -> domainEvents.add(new CompanyTraitsDeleted(v)));
        }

        private void deleteAllCompanyTraits(final Talent talent) {
            talentRepository.deleteAllCompanyTraits(talent.getId());
        }

        public void deleteAllCompanyTraits(final String talentId) {

            final Talent talent = talentRepository.findOne(talentId);
            if (Optional.ofNullable(talent).isPresent()) {
                talentRepository.deleteAllCompanyTraits(talentId);
            }
        }

        private void raiseDeleteAllEvents(final List<DomainEvent> domainEvents) {
            domainEvents.forEach(eventBus::dispatch);
        }

        private Talent findTalentWithCompanyTraits(final Talent talent) {
            return talentRepository.findOneWithCompanyTraits(talent.getId());
        }
    }
}
