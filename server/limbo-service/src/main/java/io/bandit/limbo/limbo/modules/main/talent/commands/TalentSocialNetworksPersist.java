package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.main.socialnetworks.event.SocialNetworksCreated;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentSocialNetworksPersist {

    public static class Command implements ICommand {
        private String talentId;
        private SocialNetworks socialNetworks;

        public Command(final String talentId,
                       final SocialNetworks socialNetworks) {
            this.talentId = talentId;
            this.socialNetworks = socialNetworks;
        }

        public String getTalentId() {
            return talentId;
        }

        public SocialNetworks getSocialNetworks() {
            return socialNetworks;
        }
    }

    @Named("TalentSocialNetworksPersist.CommandHandler")
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
         * Persist a SocialNetworks for a given Talents.
         */
        public CompletableFuture<SocialNetworks> handle(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                try {
                    final Talent talent = talentRepository.findOneWithSocialNetworks(command.getTalentId());
                    if (null == talent) {
                        return null;
                    }

                    final SocialNetworks socialNetworks = command.getSocialNetworks();
                    talent.addSocialNetworks(socialNetworks);
                    persister.persist(talent);

                    return socialNetworks;
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
