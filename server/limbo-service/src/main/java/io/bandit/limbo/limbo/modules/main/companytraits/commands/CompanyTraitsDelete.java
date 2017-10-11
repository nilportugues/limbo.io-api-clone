package io.bandit.limbo.limbo.modules.main.companytraits.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.companytraits.event.CompanyTraitsDeleted;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.CompanyTraitsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

public class CompanyTraitsDelete {

    public static class Command implements ICommand {
         private String companyTraitsId;

         public Command(final String companyTraitsId) {
             this.companyTraitsId = companyTraitsId;
         }

         public String getCompanyTraitsId() {
             return companyTraitsId;
        }
    }

    @Named("CompanyTraitsDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final CompanyTraitsRepository companyTraitsRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final CompanyTraitsRepository companyTraitsRepository, final EventBus eventBus) {
            this.companyTraitsRepository = companyTraitsRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the CompanyTraits by id.
         */
        public CompletableFuture<CompanyTraits> handle(final Command command) {
        //public Mono<CompanyTraits> delete(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                final CompanyTraits companyTraits = companyTraitsRepository.findOne(command.getCompanyTraitsId());

                if (null != companyTraits) {
                    this.removeDomainRelationships(companyTraits);
                    this.removePersistenceRelationships(companyTraits);
                    this.raiseDeletedEvent(companyTraits);
                }

                return companyTraits;
            });
        }

        private void removeDomainRelationships(final CompanyTraits companyTraits) {
            companyTraits.setTalent(null);
        }

        private void removePersistenceRelationships(final CompanyTraits companyTraits) {
            companyTraitsRepository.save(companyTraits);
            companyTraitsRepository.delete(companyTraits.getId());
        }

        private void raiseDeletedEvent(final CompanyTraits companyTraits) {
            eventBus.dispatch(new CompanyTraitsDeleted(companyTraits));
        }
    }

}
