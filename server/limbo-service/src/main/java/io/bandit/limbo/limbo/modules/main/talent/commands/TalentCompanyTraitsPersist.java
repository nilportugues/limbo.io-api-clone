package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.companytraits.event.CompanyTraitsCreated;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class TalentCompanyTraitsPersist {

    public static class Command implements ICommand {
        private String talentId;
        private CompanyTraits companyTraits;

        public Command(final String talentId,
                       final CompanyTraits companyTraits) {
            this.talentId = talentId;
            this.companyTraits = companyTraits;
        }

        public String getTalentId() {
            return talentId;
        }

        public CompanyTraits getCompanyTraits() {
            return companyTraits;
        }
    }

    @Named("TalentCompanyTraitsPersist.CommandHandler")
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
         * Persist a CompanyTraits for a given Talents.
         */
        public CompletableFuture<CompanyTraits> handle(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                try {
                    final Talent talent = talentRepository.findOneWithCompanyTraits(command.getTalentId());
                    if (null == talent) {
                        return null;
                    }

                    final CompanyTraits companyTraits = command.getCompanyTraits();
                    talent.addCompanyTraits(companyTraits);
                    persister.persist(talent);

                    return companyTraits;
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
