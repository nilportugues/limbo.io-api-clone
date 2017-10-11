package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.skills.event.SkillsDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentSkillsDeleteMany {

    public static class Command implements ICommand {
        private String talentId;
        private Iterable<String> skillsIds = new ArrayList<>();

        public Command(final String talentId, final Iterable<String> skillsIds) {
            this.talentId = talentId;

            if (Optional.ofNullable(skillsIds).isPresent()) {
                this.skillsIds = skillsIds;
            }
        }

        public String getTalentId() {
            return talentId;
        }

        public Iterable<String> getSkillsIds() {
            return skillsIds;
        }
    }

    @Named("TalentSkillsDeleteMany.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final TalentRepository talentRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final TalentRepository talentRepository, final EventBus eventBus) {
            this.talentRepository = talentRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete list of Skills for a given Talents.
         */
        public CompletableFuture<Talent> handle(final Command command) {
        //public Mono<Talent> deleteAllSkills(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final List<DomainEvent> domainEvents = new ArrayList<>();
                final List<String> ids = new ArrayList<>();

                final String id = command.getTalentId();
                final Talent talent = talentRepository.findOneWithSkills(id);

                final Iterable<String> skillsIds = command.getSkillsIds();
                skillsIds.forEach(ids::add);

                talent.getSkills().forEach(v -> {
                    if (ids.contains(v.getId())) {
                        domainEvents.add(new SkillsDeleted(v));
                    }
                });

                talentRepository.deleteAllSkills(id, skillsIds);
                domainEvents.forEach(eventBus::dispatch);

                return talent;
            });
        }
    }
}
