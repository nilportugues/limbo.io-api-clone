package io.bandit.limbo.limbo.modules.main.notableprojects.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.notableprojects.event.NotableProjectsCreated;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.NotableProjectsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentCreated;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class NotableProjectsTalentPersist {

    public static class Command implements ICommand {
        private String notableProjectsId;
        private Talent talent;

        public Command(final String notableProjectsId, final Talent talent) {
            this.notableProjectsId = notableProjectsId;
            this.talent = talent;
        }

        public String getNotableProjectsId() {
             return notableProjectsId;
        }

        public Talent getTalent() {
            return talent;
        }
    }

    @Named("NotableProjectsTalentPersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final NotableProjectsRepository notableProjectsRepository;
        private final NotableProjectsPersisterService persister;

        @Inject
        public CommandHandler(final NotableProjectsRepository notableProjectsRepository,
                              final NotableProjectsPersisterService persister) {

            this.notableProjectsRepository = notableProjectsRepository;
            this.persister = persister;
        }

        /**
         * Persist the current Talent value for a given NotableProjects.
         */
        public CompletableFuture<NotableProjects> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    final NotableProjects notableProjects = notableProjectsRepository.findOne(command.getNotableProjectsId());
                    if (null == notableProjects){
                        return null;
                    }

                    notableProjects.setTalent(command.getTalent());

                    return persister.persist(notableProjects);
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
