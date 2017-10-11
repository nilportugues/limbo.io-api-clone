package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentCreated;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentDeleted;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.main.talenttitle.event.TalentTitleDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentTalentTitleDelete {

    public static class Command implements ICommand {
         private String talentId;

         public Command(final String talentId) {
             this.talentId = talentId;
         }

         public String getTalentId() {
             return talentId;
        }
    }

    @Named("TalentTalentTitleDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final TalentRepository talentRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final TalentRepository talentRepository, final EventBus eventBus) {
            this.talentRepository = talentRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the current TalentTitle value for a given Talent.
         */
        public CompletableFuture<Talent> handle(final Command command) {
        //public Mono<Talent> deleteTalentTitle(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final Talent talent = talentRepository.findOneWithTalentTitle(command.getTalentId());
                talentRepository.delete(command.getTalentId());

                final List<DomainEvent> events = talent.pullEvents();
                events.forEach(v -> eventBus.dispatch(v));

                return talent;
            });
        }
    }
}
