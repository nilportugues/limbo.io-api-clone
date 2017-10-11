package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.personaltraits.event.PersonalTraitsDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentPersonalTraitsDeleteMany {

    public static class Command implements ICommand {
        private String talentId;
        private Iterable<String> personalTraitsIds = new ArrayList<>();

        public Command(final String talentId, final Iterable<String> personalTraitsIds) {
            this.talentId = talentId;

            if (Optional.ofNullable(personalTraitsIds).isPresent()) {
                this.personalTraitsIds = personalTraitsIds;
            }
        }

        public String getTalentId() {
            return talentId;
        }

        public Iterable<String> getPersonalTraitsIds() {
            return personalTraitsIds;
        }
    }

    @Named("TalentPersonalTraitsDeleteMany.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final TalentRepository talentRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final TalentRepository talentRepository, final EventBus eventBus) {
            this.talentRepository = talentRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete list of PersonalTraits for a given Talents.
         */
        public CompletableFuture<Talent> handle(final Command command) {
        //public Mono<Talent> deleteAllPersonalTraits(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final List<DomainEvent> domainEvents = new ArrayList<>();
                final List<String> ids = new ArrayList<>();

                final String id = command.getTalentId();
                final Talent talent = talentRepository.findOneWithPersonalTraits(id);

                final Iterable<String> personalTraitsIds = command.getPersonalTraitsIds();
                personalTraitsIds.forEach(ids::add);

                talent.getPersonalTraits().forEach(v -> {
                    if (ids.contains(v.getId())) {
                        domainEvents.add(new PersonalTraitsDeleted(v));
                    }
                });

                talentRepository.deleteAllPersonalTraits(id, personalTraitsIds);
                domainEvents.forEach(eventBus::dispatch);

                return talent;
            });
        }
    }
}
