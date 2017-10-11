package io.bandit.limbo.limbo.modules.main.skills.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.skills.event.SkillsCreated;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.SkillsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentCreated;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SkillsTalentPersist {

    public static class Command implements ICommand {
        private String skillsId;
        private Talent talent;

        public Command(final String skillsId, final Talent talent) {
            this.skillsId = skillsId;
            this.talent = talent;
        }

        public String getSkillsId() {
             return skillsId;
        }

        public Talent getTalent() {
            return talent;
        }
    }

    @Named("SkillsTalentPersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final SkillsRepository skillsRepository;
        private final SkillsPersisterService persister;

        @Inject
        public CommandHandler(final SkillsRepository skillsRepository,
                              final SkillsPersisterService persister) {

            this.skillsRepository = skillsRepository;
            this.persister = persister;
        }

        /**
         * Persist the current Talent value for a given Skills.
         */
        public CompletableFuture<Skills> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    final Skills skills = skillsRepository.findOne(command.getSkillsId());
                    if (null == skills){
                        return null;
                    }

                    skills.setTalent(command.getTalent());

                    return persister.persist(skills);
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
