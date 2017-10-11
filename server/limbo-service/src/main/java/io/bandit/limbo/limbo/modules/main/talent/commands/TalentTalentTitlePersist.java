package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentCreated;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.main.talenttitle.event.TalentTitleCreated;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentTalentTitlePersist {

    public static class Command implements ICommand {
        private String talentId;
        private TalentTitle talentTitle;

        public Command(final String talentId, final TalentTitle talentTitle) {
            this.talentId = talentId;
            this.talentTitle = talentTitle;
        }

        public String getTalentId() {
             return talentId;
        }

        public TalentTitle getTalentTitle() {
            return talentTitle;
        }
    }

    @Named("TalentTalentTitlePersist.CommandHandler")
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
         * Persist the current TalentTitle value for a given Talent.
         */
        public CompletableFuture<Talent> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    final Talent talent = talentRepository.findOne(command.getTalentId());
                    if (null == talent){
                        return null;
                    }

                    talent.setTalentTitle(command.getTalentTitle());

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
