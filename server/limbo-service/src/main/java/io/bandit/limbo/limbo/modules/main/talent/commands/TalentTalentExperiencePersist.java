package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentCreated;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.main.talentexperience.event.TalentExperienceCreated;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentTalentExperiencePersist {

    public static class Command implements ICommand {
        private String talentId;
        private TalentExperience talentExperience;

        public Command(final String talentId, final TalentExperience talentExperience) {
            this.talentId = talentId;
            this.talentExperience = talentExperience;
        }

        public String getTalentId() {
             return talentId;
        }

        public TalentExperience getTalentExperience() {
            return talentExperience;
        }
    }

    @Named("TalentTalentExperiencePersist.CommandHandler")
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
         * Persist the current TalentExperience value for a given Talent.
         */
        public CompletableFuture<Talent> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    final Talent talent = talentRepository.findOne(command.getTalentId());
                    if (null == talent){
                        return null;
                    }

                    talent.setTalentExperience(command.getTalentExperience());

                    return persister.persist(talent);
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
