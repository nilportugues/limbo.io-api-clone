package io.bandit.limbo.limbo.modules.main.talentexperience.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.main.talentexperience.event.TalentExperienceDeleted;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.TalentExperienceRepository;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

public class TalentExperienceDelete {

    public static class Command implements ICommand {
         private String talentExperienceId;

         public Command(final String talentExperienceId) {
             this.talentExperienceId = talentExperienceId;
         }

         public String getTalentExperienceId() {
             return talentExperienceId;
        }
    }

    @Named("TalentExperienceDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final TalentExperienceRepository talentExperienceRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final TalentExperienceRepository talentExperienceRepository, final EventBus eventBus) {
            this.talentExperienceRepository = talentExperienceRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the TalentExperience by id.
         */
        public CompletableFuture<TalentExperience> handle(final Command command) {
        //public Mono<TalentExperience> delete(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                final TalentExperience talentExperience = talentExperienceRepository.findOne(command.getTalentExperienceId());

                if (null != talentExperience) {
                    this.removeDomainRelationships(talentExperience);
                    this.removePersistenceRelationships(talentExperience);
                    this.raiseDeletedEvent(talentExperience);
                }

                return talentExperience;
            });
        }

        private void removeDomainRelationships(final TalentExperience talentExperience) {
        }

        private void removePersistenceRelationships(final TalentExperience talentExperience) {
            talentExperienceRepository.save(talentExperience);
            talentExperienceRepository.delete(talentExperience.getId());
        }

        private void raiseDeletedEvent(final TalentExperience talentExperience) {
            eventBus.dispatch(new TalentExperienceDeleted(talentExperience));
        }
    }

}
