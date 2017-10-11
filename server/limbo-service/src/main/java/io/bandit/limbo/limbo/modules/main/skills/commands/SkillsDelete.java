package io.bandit.limbo.limbo.modules.main.skills.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.skills.event.SkillsDeleted;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.SkillsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

public class SkillsDelete {

    public static class Command implements ICommand {
         private String skillsId;

         public Command(final String skillsId) {
             this.skillsId = skillsId;
         }

         public String getSkillsId() {
             return skillsId;
        }
    }

    @Named("SkillsDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final SkillsRepository skillsRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final SkillsRepository skillsRepository, final EventBus eventBus) {
            this.skillsRepository = skillsRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the Skills by id.
         */
        public CompletableFuture<Skills> handle(final Command command) {
        //public Mono<Skills> delete(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                final Skills skills = skillsRepository.findOne(command.getSkillsId());

                if (null != skills) {
                    this.removeDomainRelationships(skills);
                    this.removePersistenceRelationships(skills);
                    this.raiseDeletedEvent(skills);
                }

                return skills;
            });
        }

        private void removeDomainRelationships(final Skills skills) {
            skills.setTalent(null);
        }

        private void removePersistenceRelationships(final Skills skills) {
            skillsRepository.save(skills);
            skillsRepository.delete(skills.getId());
        }

        private void raiseDeletedEvent(final Skills skills) {
            eventBus.dispatch(new SkillsDeleted(skills));
        }
    }

}
