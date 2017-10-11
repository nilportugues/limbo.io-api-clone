package io.bandit.limbo.limbo.modules.main.companytraits.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.companytraits.event.CompanyTraitsCreated;
import io.bandit.limbo.limbo.modules.main.companytraits.event.CompanyTraitsDeleted;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.CompanyTraitsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class CompanyTraitsTalentDelete {

    public static class Command implements ICommand {
         private String companyTraitsId;

         public Command(final String companyTraitsId) {
             this.companyTraitsId = companyTraitsId;
         }

         public String getCompanyTraitsId() {
             return companyTraitsId;
        }
    }

    @Named("CompanyTraitsTalentDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final CompanyTraitsRepository companyTraitsRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final CompanyTraitsRepository companyTraitsRepository, final EventBus eventBus) {
            this.companyTraitsRepository = companyTraitsRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the current Talent value for a given CompanyTraits.
         */
        public CompletableFuture<CompanyTraits> handle(final Command command) {
        //public Mono<CompanyTraits> deleteTalent(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final CompanyTraits companyTraits = companyTraitsRepository.findOneWithTalent(command.getCompanyTraitsId());
                companyTraitsRepository.delete(command.getCompanyTraitsId());

                final List<DomainEvent> events = companyTraits.pullEvents();
                events.forEach(v -> eventBus.dispatch(v));

                return companyTraits;
            });
        }
    }
}
