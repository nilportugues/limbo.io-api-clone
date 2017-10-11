package io.bandit.limbo.limbo.modules.main.personaltraits.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.personaltraits.event.PersonalTraitsDeleted;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.PersonalTraitsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

public class PersonalTraitsDelete {

    public static class Command implements ICommand {
         private String personalTraitsId;

         public Command(final String personalTraitsId) {
             this.personalTraitsId = personalTraitsId;
         }

         public String getPersonalTraitsId() {
             return personalTraitsId;
        }
    }

    @Named("PersonalTraitsDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final PersonalTraitsRepository personalTraitsRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final PersonalTraitsRepository personalTraitsRepository, final EventBus eventBus) {
            this.personalTraitsRepository = personalTraitsRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the PersonalTraits by id.
         */
        public CompletableFuture<PersonalTraits> handle(final Command command) {
        //public Mono<PersonalTraits> delete(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                final PersonalTraits personalTraits = personalTraitsRepository.findOne(command.getPersonalTraitsId());

                if (null != personalTraits) {
                    this.removeDomainRelationships(personalTraits);
                    this.removePersistenceRelationships(personalTraits);
                    this.raiseDeletedEvent(personalTraits);
                }

                return personalTraits;
            });
        }

        private void removeDomainRelationships(final PersonalTraits personalTraits) {
            personalTraits.setTalent(null);
        }

        private void removePersistenceRelationships(final PersonalTraits personalTraits) {
            personalTraitsRepository.save(personalTraits);
            personalTraitsRepository.delete(personalTraits.getId());
        }

        private void raiseDeletedEvent(final PersonalTraits personalTraits) {
            eventBus.dispatch(new PersonalTraitsDeleted(personalTraits));
        }
    }

}
