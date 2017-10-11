package io.bandit.limbo.limbo.modules.main.talentrole.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.modules.main.talentrole.event.TalentRoleDeleted;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.TalentRoleRepository;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

public class TalentRoleDelete {

    public static class Command implements ICommand {
         private String talentRoleId;

         public Command(final String talentRoleId) {
             this.talentRoleId = talentRoleId;
         }

         public String getTalentRoleId() {
             return talentRoleId;
        }
    }

    @Named("TalentRoleDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final TalentRoleRepository talentRoleRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final TalentRoleRepository talentRoleRepository, final EventBus eventBus) {
            this.talentRoleRepository = talentRoleRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the TalentRole by id.
         */
        public CompletableFuture<TalentRole> handle(final Command command) {
        //public Mono<TalentRole> delete(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                final TalentRole talentRole = talentRoleRepository.findOne(command.getTalentRoleId());

                if (null != talentRole) {
                    this.removeDomainRelationships(talentRole);
                    this.removePersistenceRelationships(talentRole);
                    this.raiseDeletedEvent(talentRole);
                }

                return talentRole;
            });
        }

        private void removeDomainRelationships(final TalentRole talentRole) {
        }

        private void removePersistenceRelationships(final TalentRole talentRole) {
            talentRoleRepository.save(talentRole);
            talentRoleRepository.delete(talentRole.getId());
        }

        private void raiseDeletedEvent(final TalentRole talentRole) {
            eventBus.dispatch(new TalentRoleDeleted(talentRole));
        }
    }

}
