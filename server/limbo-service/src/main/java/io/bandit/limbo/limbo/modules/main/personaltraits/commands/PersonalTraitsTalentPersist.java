package io.bandit.limbo.limbo.modules.main.personaltraits.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.personaltraits.event.PersonalTraitsCreated;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.PersonalTraitsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentCreated;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class PersonalTraitsTalentPersist {

    public static class Command implements ICommand {
        private String personalTraitsId;
        private Talent talent;

        public Command(final String personalTraitsId, final Talent talent) {
            this.personalTraitsId = personalTraitsId;
            this.talent = talent;
        }

        public String getPersonalTraitsId() {
             return personalTraitsId;
        }

        public Talent getTalent() {
            return talent;
        }
    }

    @Named("PersonalTraitsTalentPersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final PersonalTraitsRepository personalTraitsRepository;
        private final PersonalTraitsPersisterService persister;

        @Inject
        public CommandHandler(final PersonalTraitsRepository personalTraitsRepository,
                              final PersonalTraitsPersisterService persister) {

            this.personalTraitsRepository = personalTraitsRepository;
            this.persister = persister;
        }

        /**
         * Persist the current Talent value for a given PersonalTraits.
         */
        public CompletableFuture<PersonalTraits> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    final PersonalTraits personalTraits = personalTraitsRepository.findOne(command.getPersonalTraitsId());
                    if (null == personalTraits){
                        return null;
                    }

                    personalTraits.setTalent(command.getTalent());

                    return persister.persist(personalTraits);
                } catch (Throwable throwable) {
                    throwAsyncException(throwable);
                    return null;
                }
            });
        }

        private void throwAsyncException(final Throwable throwable) {
            final Thread thread = Thread.currentThread();
            thread.getUncaughtExceptionHandler().uncaughtException(thread, throwable);
        }
    }
}
