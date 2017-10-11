package io.bandit.limbo.limbo.modules.main.skills.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.skills.event.SkillsCreated;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.SkillsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

public class SkillsPersist {

    public static class Command implements ICommand {
        private Skills skills;

        public Command(final Skills skills) {
            this.skills = skills;
        }

        public Skills getSkills() {
            return skills;
        }
    }

    @Named("SkillsPersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final SkillsPersisterService persister;

        @Inject
        public CommandHandler(final SkillsPersisterService service) {
            this.persister = service;
        }

        public CompletableFuture<Skills> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return persister.persist(command.getSkills());
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
