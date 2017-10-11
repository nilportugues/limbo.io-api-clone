package io.bandit.limbo.limbo.modules.main.notableprojects.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.notableprojects.event.NotableProjectsDeleted;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.NotableProjectsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

public class NotableProjectsDelete {

    public static class Command implements ICommand {
         private String notableProjectsId;

         public Command(final String notableProjectsId) {
             this.notableProjectsId = notableProjectsId;
         }

         public String getNotableProjectsId() {
             return notableProjectsId;
        }
    }

    @Named("NotableProjectsDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final NotableProjectsRepository notableProjectsRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final NotableProjectsRepository notableProjectsRepository, final EventBus eventBus) {
            this.notableProjectsRepository = notableProjectsRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the NotableProjects by id.
         */
        public CompletableFuture<NotableProjects> handle(final Command command) {
        //public Mono<NotableProjects> delete(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                final NotableProjects notableProjects = notableProjectsRepository.findOne(command.getNotableProjectsId());

                if (null != notableProjects) {
                    this.removeDomainRelationships(notableProjects);
                    this.removePersistenceRelationships(notableProjects);
                    this.raiseDeletedEvent(notableProjects);
                }

                return notableProjects;
            });
        }

        private void removeDomainRelationships(final NotableProjects notableProjects) {
            notableProjects.setTalent(null);
        }

        private void removePersistenceRelationships(final NotableProjects notableProjects) {
            notableProjectsRepository.save(notableProjects);
            notableProjectsRepository.delete(notableProjects.getId());
        }

        private void raiseDeletedEvent(final NotableProjects notableProjects) {
            eventBus.dispatch(new NotableProjectsDeleted(notableProjects));
        }
    }

}
