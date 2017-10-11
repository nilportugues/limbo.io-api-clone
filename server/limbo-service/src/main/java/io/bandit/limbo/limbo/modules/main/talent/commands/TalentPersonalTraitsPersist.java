package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.personaltraits.event.PersonalTraitsCreated;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentPersonalTraitsPersist {

    public static class Command implements ICommand {
        private String talentId;
        private PersonalTraits personalTraits;

        public Command(final String talentId,
                       final PersonalTraits personalTraits) {
            this.talentId = talentId;
            this.personalTraits = personalTraits;
        }

        public String getTalentId() {
            return talentId;
        }

        public PersonalTraits getPersonalTraits() {
            return personalTraits;
        }
    }

    @Named("TalentPersonalTraitsPersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final TalentRepository talentRepository;
        private final TalentPersisterService persister;

        @Inject
        public CommandHandler(final TalentRepository talentRepository,
                              final TalentPersisterService persister) {

            this.talentRepository = talentRepository;
            this.persister = persister;
        }

        /**
         * Persist a PersonalTraits for a given Talents.
         */
        public CompletableFuture<PersonalTraits> handle(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                try {
                    final Talent talent = talentRepository.findOneWithPersonalTraits(command.getTalentId());
                    if (null == talent) {
                        return null;
                    }

                    final PersonalTraits personalTraits = command.getPersonalTraits();
                    talent.addPersonalTraits(personalTraits);
                    persister.persist(talent);

                    return personalTraits;
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
