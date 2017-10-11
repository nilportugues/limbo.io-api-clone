package io.bandit.limbo.limbo.modules.main.companytraits.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.companytraits.event.CompanyTraitsCreated;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.CompanyTraitsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentCreated;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class CompanyTraitsTalentPersist {

    public static class Command implements ICommand {
        private String companyTraitsId;
        private Talent talent;

        public Command(final String companyTraitsId, final Talent talent) {
            this.companyTraitsId = companyTraitsId;
            this.talent = talent;
        }

        public String getCompanyTraitsId() {
             return companyTraitsId;
        }

        public Talent getTalent() {
            return talent;
        }
    }

    @Named("CompanyTraitsTalentPersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final CompanyTraitsRepository companyTraitsRepository;
        private final CompanyTraitsPersisterService persister;

        @Inject
        public CommandHandler(final CompanyTraitsRepository companyTraitsRepository,
                              final CompanyTraitsPersisterService persister) {

            this.companyTraitsRepository = companyTraitsRepository;
            this.persister = persister;
        }

        /**
         * Persist the current Talent value for a given CompanyTraits.
         */
        public CompletableFuture<CompanyTraits> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    final CompanyTraits companyTraits = companyTraitsRepository.findOne(command.getCompanyTraitsId());
                    if (null == companyTraits){
                        return null;
                    }

                    companyTraits.setTalent(command.getTalent());

                    return persister.persist(companyTraits);
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
