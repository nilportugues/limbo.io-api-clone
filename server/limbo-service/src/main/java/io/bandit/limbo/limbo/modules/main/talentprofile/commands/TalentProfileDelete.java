package io.bandit.limbo.limbo.modules.main.talentprofile.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.main.talentprofile.event.TalentProfileDeleted;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.TalentProfileRepository;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

public class TalentProfileDelete {

    public static class Command implements ICommand {
         private String talentProfileId;

         public Command(final String talentProfileId) {
             this.talentProfileId = talentProfileId;
         }

         public String getTalentProfileId() {
             return talentProfileId;
        }
    }

    @Named("TalentProfileDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final TalentProfileRepository talentProfileRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final TalentProfileRepository talentProfileRepository, final EventBus eventBus) {
            this.talentProfileRepository = talentProfileRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the TalentProfile by id.
         */
        public CompletableFuture<TalentProfile> handle(final Command command) {
        //public Mono<TalentProfile> delete(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                final TalentProfile talentProfile = talentProfileRepository.findOne(command.getTalentProfileId());

                if (null != talentProfile) {
                    this.removeDomainRelationships(talentProfile);
                    this.removePersistenceRelationships(talentProfile);
                    this.raiseDeletedEvent(talentProfile);
                }

                return talentProfile;
            });
        }

        private void removeDomainRelationships(final TalentProfile talentProfile) {
        }

        private void removePersistenceRelationships(final TalentProfile talentProfile) {
            talentProfileRepository.save(talentProfile);
            talentProfileRepository.delete(talentProfile.getId());
        }

        private void raiseDeletedEvent(final TalentProfile talentProfile) {
            eventBus.dispatch(new TalentProfileDeleted(talentProfile));
        }
    }

}
