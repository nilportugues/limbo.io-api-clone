package io.bandit.limbo.limbo.modules.main.notableprojects.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.notableprojects.event.NotableProjectsCreated;
import io.bandit.limbo.limbo.modules.main.notableprojects.event.NotableProjectsDeleted;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.NotableProjectsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class NotableProjectsTalentDelete {

    public static class Command implements ICommand {
         private String notableProjectsId;

         public Command(final String notableProjectsId) {
             this.notableProjectsId = notableProjectsId;
         }

         public String getNotableProjectsId() {
             return notableProjectsId;
        }
    }

    @Named("NotableProjectsTalentDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final NotableProjectsRepository notableProjectsRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final NotableProjectsRepository notableProjectsRepository, final EventBus eventBus) {
            this.notableProjectsRepository = notableProjectsRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the current Talent value for a given NotableProjects.
         */
        public CompletableFuture<NotableProjects> handle(final Command command) {
        //public Mono<NotableProjects> deleteTalent(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final NotableProjects notableProjects = notableProjectsRepository.findOneWithTalent(command.getNotableProjectsId());
                notableProjectsRepository.delete(command.getNotableProjectsId());

                final List<DomainEvent> events = notableProjects.pullEvents();
                events.forEach(v -> eventBus.dispatch(v));

                return notableProjects;
            });
        }
    }
}
