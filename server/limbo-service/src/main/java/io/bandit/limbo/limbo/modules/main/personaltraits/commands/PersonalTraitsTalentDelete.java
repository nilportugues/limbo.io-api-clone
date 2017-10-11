package io.bandit.limbo.limbo.modules.main.personaltraits.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.personaltraits.event.PersonalTraitsCreated;
import io.bandit.limbo.limbo.modules.main.personaltraits.event.PersonalTraitsDeleted;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.PersonalTraitsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class PersonalTraitsTalentDelete {

    public static class Command implements ICommand {
         private String personalTraitsId;

         public Command(final String personalTraitsId) {
             this.personalTraitsId = personalTraitsId;
         }

         public String getPersonalTraitsId() {
             return personalTraitsId;
        }
    }

    @Named("PersonalTraitsTalentDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final PersonalTraitsRepository personalTraitsRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final PersonalTraitsRepository personalTraitsRepository, final EventBus eventBus) {
            this.personalTraitsRepository = personalTraitsRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the current Talent value for a given PersonalTraits.
         */
        public CompletableFuture<PersonalTraits> handle(final Command command) {
        //public Mono<PersonalTraits> deleteTalent(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final PersonalTraits personalTraits = personalTraitsRepository.findOneWithTalent(command.getPersonalTraitsId());
                personalTraitsRepository.delete(command.getPersonalTraitsId());

                final List<DomainEvent> events = personalTraits.pullEvents();
                events.forEach(v -> eventBus.dispatch(v));

                return personalTraits;
            });
        }
    }
}
