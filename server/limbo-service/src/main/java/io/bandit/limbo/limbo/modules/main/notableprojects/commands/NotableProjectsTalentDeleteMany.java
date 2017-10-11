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

public class NotableProjectsTalentDeleteMany {

    public static class Command implements ICommand {
        private Iterable<String> notableProjectsIds = new ArrayList<>();

        public Command(final Iterable<String> notableProjectsIds) {

            if (Optional.ofNullable(notableProjectsIds).isPresent()) {
                this.notableProjectsIds = notableProjectsIds;
            }
        }

        public Iterable<String> getNotableProjectsIds() {
            return notableProjectsIds;
        }
    }

    @Named("NotableProjectsTalentDeleteMany.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final NotableProjectsRepository notableProjectsRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final NotableProjectsRepository notableProjectsRepository, final EventBus eventBus) {
            this.notableProjectsRepository = notableProjectsRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the current Talent value for a given set of NotableProjects.
         */
        public CompletableFuture<List<NotableProjects>> handle(final Command command) {
        //public Mono<List<NotableProjects>> deleteTalents(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final List<NotableProjects> list = new ArrayList<>();

                command.getNotableProjectsIds().forEach(notableProjectsId -> {
                    final NotableProjects notableProjects = notableProjectsRepository.findOneWithTalent(notableProjectsId);
                    notableProjectsRepository.delete(notableProjectsId);

                    final List<DomainEvent> events = notableProjects.pullEvents();
                    events.forEach(v -> eventBus.dispatch(v));

                    list.add(notableProjects);
                });

                return list;
            });
        }
    }
}
