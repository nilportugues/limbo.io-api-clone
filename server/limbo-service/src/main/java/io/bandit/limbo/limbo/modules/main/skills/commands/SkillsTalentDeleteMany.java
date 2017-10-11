package io.bandit.limbo.limbo.modules.main.skills.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.skills.event.SkillsCreated;
import io.bandit.limbo.limbo.modules.main.skills.event.SkillsDeleted;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.SkillsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SkillsTalentDeleteMany {

    public static class Command implements ICommand {
        private Iterable<String> skillsIds = new ArrayList<>();

        public Command(final Iterable<String> skillsIds) {

            if (Optional.ofNullable(skillsIds).isPresent()) {
                this.skillsIds = skillsIds;
            }
        }

        public Iterable<String> getSkillsIds() {
            return skillsIds;
        }
    }

    @Named("SkillsTalentDeleteMany.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final SkillsRepository skillsRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final SkillsRepository skillsRepository, final EventBus eventBus) {
            this.skillsRepository = skillsRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the current Talent value for a given set of Skills.
         */
        public CompletableFuture<List<Skills>> handle(final Command command) {
        //public Mono<List<Skills>> deleteTalents(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final List<Skills> list = new ArrayList<>();

                command.getSkillsIds().forEach(skillsId -> {
                    final Skills skills = skillsRepository.findOneWithTalent(skillsId);
                    skillsRepository.delete(skillsId);

                    final List<DomainEvent> events = skills.pullEvents();
                    events.forEach(v -> eventBus.dispatch(v));

                    list.add(skills);
                });

                return list;
            });
        }
    }
}
