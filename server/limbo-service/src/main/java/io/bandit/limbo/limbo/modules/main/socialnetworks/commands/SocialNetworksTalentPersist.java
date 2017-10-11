package io.bandit.limbo.limbo.modules.main.socialnetworks.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.main.socialnetworks.event.SocialNetworksCreated;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.SocialNetworksRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentCreated;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SocialNetworksTalentPersist {

    public static class Command implements ICommand {
        private String socialNetworksId;
        private Talent talent;

        public Command(final String socialNetworksId, final Talent talent) {
            this.socialNetworksId = socialNetworksId;
            this.talent = talent;
        }

        public String getSocialNetworksId() {
             return socialNetworksId;
        }

        public Talent getTalent() {
            return talent;
        }
    }

    @Named("SocialNetworksTalentPersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final SocialNetworksRepository socialNetworksRepository;
        private final SocialNetworksPersisterService persister;

        @Inject
        public CommandHandler(final SocialNetworksRepository socialNetworksRepository,
                              final SocialNetworksPersisterService persister) {

            this.socialNetworksRepository = socialNetworksRepository;
            this.persister = persister;
        }

        /**
         * Persist the current Talent value for a given SocialNetworks.
         */
        public CompletableFuture<SocialNetworks> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    final SocialNetworks socialNetworks = socialNetworksRepository.findOne(command.getSocialNetworksId());
                    if (null == socialNetworks){
                        return null;
                    }

                    socialNetworks.setTalent(command.getTalent());

                    return persister.persist(socialNetworks);
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
