package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.notableprojects.event.NotableProjectsCreated;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentNotableProjectsPersist {

    public static class Command implements ICommand {
        private String talentId;
        private NotableProjects notableProjects;

        public Command(final String talentId,
                       final NotableProjects notableProjects) {
            this.talentId = talentId;
            this.notableProjects = notableProjects;
        }

        public String getTalentId() {
            return talentId;
        }

        public NotableProjects getNotableProjects() {
            return notableProjects;
        }
    }

    @Named("TalentNotableProjectsPersist.CommandHandler")
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
         * Persist a NotableProjects for a given Talents.
         */
        public CompletableFuture<NotableProjects> handle(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                try {
                    final Talent talent = talentRepository.findOneWithNotableProjects(command.getTalentId());
                    if (null == talent) {
                        return null;
                    }

                    final NotableProjects notableProjects = command.getNotableProjects();
                    talent.addNotableProjects(notableProjects);
                    persister.persist(talent);

                    return notableProjects;
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
