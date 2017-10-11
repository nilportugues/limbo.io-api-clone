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

public class PersonalTraitsTalentDeleteMany {

    public static class Command implements ICommand {
        private Iterable<String> personalTraitsIds = new ArrayList<>();

        public Command(final Iterable<String> personalTraitsIds) {

            if (Optional.ofNullable(personalTraitsIds).isPresent()) {
                this.personalTraitsIds = personalTraitsIds;
            }
        }

        public Iterable<String> getPersonalTraitsIds() {
            return personalTraitsIds;
        }
    }

    @Named("PersonalTraitsTalentDeleteMany.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final PersonalTraitsRepository personalTraitsRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final PersonalTraitsRepository personalTraitsRepository, final EventBus eventBus) {
            this.personalTraitsRepository = personalTraitsRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the current Talent value for a given set of PersonalTraits.
         */
        public CompletableFuture<List<PersonalTraits>> handle(final Command command) {
        //public Mono<List<PersonalTraits>> deleteTalents(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final List<PersonalTraits> list = new ArrayList<>();

                command.getPersonalTraitsIds().forEach(personalTraitsId -> {
                    final PersonalTraits personalTraits = personalTraitsRepository.findOneWithTalent(personalTraitsId);
                    personalTraitsRepository.delete(personalTraitsId);

                    final List<DomainEvent> events = personalTraits.pullEvents();
                    events.forEach(v -> eventBus.dispatch(v));

                    list.add(personalTraits);
                });

                return list;
            });
        }
    }
}
