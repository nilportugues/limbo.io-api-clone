package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentCreated;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.modules.main.talentrole.event.TalentRoleCreated;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentTalentRolePersist {

    public static class Command implements ICommand {
        private String talentId;
        private TalentRole talentRole;

        public Command(final String talentId, final TalentRole talentRole) {
            this.talentId = talentId;
            this.talentRole = talentRole;
        }

        public String getTalentId() {
             return talentId;
        }

        public TalentRole getTalentRole() {
            return talentRole;
        }
    }

    @Named("TalentTalentRolePersist.CommandHandler")
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
         * Persist the current TalentRole value for a given Talent.
         */
        public CompletableFuture<Talent> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    final Talent talent = talentRepository.findOne(command.getTalentId());
                    if (null == talent){
                        return null;
                    }

                    talent.setTalentRole(command.getTalentRole());

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
