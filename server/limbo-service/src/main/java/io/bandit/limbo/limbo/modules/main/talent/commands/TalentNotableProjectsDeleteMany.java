package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.notableprojects.event.NotableProjectsDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentNotableProjectsDeleteMany {

    public static class Command implements ICommand {
        private String talentId;
        private Iterable<String> notableProjectsIds = new ArrayList<>();

        public Command(final String talentId, final Iterable<String> notableProjectsIds) {
            this.talentId = talentId;

            if (Optional.ofNullable(notableProjectsIds).isPresent()) {
                this.notableProjectsIds = notableProjectsIds;
            }
        }

        public String getTalentId() {
            return talentId;
        }

        public Iterable<String> getNotableProjectsIds() {
            return notableProjectsIds;
        }
    }

    @Named("TalentNotableProjectsDeleteMany.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final TalentRepository talentRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final TalentRepository talentRepository, final EventBus eventBus) {
            this.talentRepository = talentRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete list of NotableProjects for a given Talents.
         */
        public CompletableFuture<Talent> handle(final Command command) {
        //public Mono<Talent> deleteAllNotableProjects(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final List<DomainEvent> domainEvents = new ArrayList<>();
                final List<String> ids = new ArrayList<>();

                final String id = command.getTalentId();
                final Talent talent = talentRepository.findOneWithNotableProjects(id);

                final Iterable<String> notableProjectsIds = command.getNotableProjectsIds();
                notableProjectsIds.forEach(ids::add);

                talent.getNotableProjects().forEach(v -> {
                    if (ids.contains(v.getId())) {
                        domainEvents.add(new NotableProjectsDeleted(v));
                    }
                });

                talentRepository.deleteAllNotableProjects(id, notableProjectsIds);
                domainEvents.forEach(eventBus::dispatch);

                return talent;
            });
        }
    }
}
