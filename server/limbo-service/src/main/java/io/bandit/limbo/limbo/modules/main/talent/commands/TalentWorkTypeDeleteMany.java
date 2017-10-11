package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentCreated;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentDeleted;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.main.worktype.event.WorkTypeDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentWorkTypeDeleteMany {

    public static class Command implements ICommand {
        private Iterable<String> talentIds = new ArrayList<>();

        public Command(final Iterable<String> talentIds) {

            if (Optional.ofNullable(talentIds).isPresent()) {
                this.talentIds = talentIds;
            }
        }

        public Iterable<String> getTalentIds() {
            return talentIds;
        }
    }

    @Named("TalentWorkTypeDeleteMany.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final TalentRepository talentRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final TalentRepository talentRepository, final EventBus eventBus) {
            this.talentRepository = talentRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the current WorkType value for a given set of Talents.
         */
        public CompletableFuture<List<Talent>> handle(final Command command) {
        //public Mono<List<Talent>> deleteWorkTypes(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final List<Talent> list = new ArrayList<>();

                command.getTalentIds().forEach(talentId -> {
                    final Talent talent = talentRepository.findOneWithWorkType(talentId);
                    talentRepository.delete(talentId);

                    final List<DomainEvent> events = talent.pullEvents();
                    events.forEach(v -> eventBus.dispatch(v));

                    list.add(talent);
                });

                return list;
            });
        }
    }
}
