package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.socialnetworks.event.SocialNetworksDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentSocialNetworksDeleteMany {

    public static class Command implements ICommand {
        private String talentId;
        private Iterable<String> socialNetworksIds = new ArrayList<>();

        public Command(final String talentId, final Iterable<String> socialNetworksIds) {
            this.talentId = talentId;

            if (Optional.ofNullable(socialNetworksIds).isPresent()) {
                this.socialNetworksIds = socialNetworksIds;
            }
        }

        public String getTalentId() {
            return talentId;
        }

        public Iterable<String> getSocialNetworksIds() {
            return socialNetworksIds;
        }
    }

    @Named("TalentSocialNetworksDeleteMany.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final TalentRepository talentRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final TalentRepository talentRepository, final EventBus eventBus) {
            this.talentRepository = talentRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete list of SocialNetworks for a given Talents.
         */
        public CompletableFuture<Talent> handle(final Command command) {
        //public Mono<Talent> deleteAllSocialNetworks(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final List<DomainEvent> domainEvents = new ArrayList<>();
                final List<String> ids = new ArrayList<>();

                final String id = command.getTalentId();
                final Talent talent = talentRepository.findOneWithSocialNetworks(id);

                final Iterable<String> socialNetworksIds = command.getSocialNetworksIds();
                socialNetworksIds.forEach(ids::add);

                talent.getSocialNetworks().forEach(v -> {
                    if (ids.contains(v.getId())) {
                        domainEvents.add(new SocialNetworksDeleted(v));
                    }
                });

                talentRepository.deleteAllSocialNetworks(id, socialNetworksIds);
                domainEvents.forEach(eventBus::dispatch);

                return talent;
            });
        }
    }
}
