package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.skills.event.SkillsCreated;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentSkillsPersist {

    public static class Command implements ICommand {
        private String talentId;
        private Skills skills;

        public Command(final String talentId,
                       final Skills skills) {
            this.talentId = talentId;
            this.skills = skills;
        }

        public String getTalentId() {
            return talentId;
        }

        public Skills getSkills() {
            return skills;
        }
    }

    @Named("TalentSkillsPersist.CommandHandler")
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
         * Persist a Skills for a given Talents.
         */
        public CompletableFuture<Skills> handle(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                try {
                    final Talent talent = talentRepository.findOneWithSkills(command.getTalentId());
                    if (null == talent) {
                        return null;
                    }

                    final Skills skills = command.getSkills();
                    talent.addSkills(skills);
                    persister.persist(talent);

                    return skills;
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
