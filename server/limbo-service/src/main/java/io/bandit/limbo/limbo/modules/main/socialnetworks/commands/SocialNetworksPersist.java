package io.bandit.limbo.limbo.modules.main.socialnetworks.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.main.socialnetworks.event.SocialNetworksCreated;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.SocialNetworksRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

public class SocialNetworksPersist {

    public static class Command implements ICommand {
        private SocialNetworks socialNetworks;

        public Command(final SocialNetworks socialNetworks) {
            this.socialNetworks = socialNetworks;
        }

        public SocialNetworks getSocialNetworks() {
            return socialNetworks;
        }
    }

    @Named("SocialNetworksPersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final SocialNetworksPersisterService persister;

        @Inject
        public CommandHandler(final SocialNetworksPersisterService service) {
            this.persister = service;
        }

        public CompletableFuture<SocialNetworks> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return persister.persist(command.getSocialNetworks());
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
