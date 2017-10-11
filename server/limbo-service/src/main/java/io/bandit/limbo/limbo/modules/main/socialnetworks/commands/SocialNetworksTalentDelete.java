package io.bandit.limbo.limbo.modules.main.socialnetworks.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.main.socialnetworks.event.SocialNetworksCreated;
import io.bandit.limbo.limbo.modules.main.socialnetworks.event.SocialNetworksDeleted;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.SocialNetworksRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SocialNetworksTalentDelete {

    public static class Command implements ICommand {
         private String socialNetworksId;

         public Command(final String socialNetworksId) {
             this.socialNetworksId = socialNetworksId;
         }

         public String getSocialNetworksId() {
             return socialNetworksId;
        }
    }

    @Named("SocialNetworksTalentDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final SocialNetworksRepository socialNetworksRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final SocialNetworksRepository socialNetworksRepository, final EventBus eventBus) {
            this.socialNetworksRepository = socialNetworksRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the current Talent value for a given SocialNetworks.
         */
        public CompletableFuture<SocialNetworks> handle(final Command command) {
        //public Mono<SocialNetworks> deleteTalent(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final SocialNetworks socialNetworks = socialNetworksRepository.findOneWithTalent(command.getSocialNetworksId());
                socialNetworksRepository.delete(command.getSocialNetworksId());

                final List<DomainEvent> events = socialNetworks.pullEvents();
                events.forEach(v -> eventBus.dispatch(v));

                return socialNetworks;
            });
        }
    }
}
