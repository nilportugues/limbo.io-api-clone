package io.bandit.limbo.limbo.modules.main.talenttitle.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.main.talenttitle.event.TalentTitleCreated;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.TalentTitleRepository;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

public class TalentTitlePersist {

    public static class Command implements ICommand {
        private TalentTitle talentTitle;

        public Command(final TalentTitle talentTitle) {
            this.talentTitle = talentTitle;
        }

        public TalentTitle getTalentTitle() {
            return talentTitle;
        }
    }

    @Named("TalentTitlePersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final TalentTitlePersisterService persister;

        @Inject
        public CommandHandler(final TalentTitlePersisterService service) {
            this.persister = service;
        }

        public CompletableFuture<TalentTitle> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return persister.persist(command.getTalentTitle());
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
