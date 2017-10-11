package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentCreated;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

public class TalentPersist {

    public static class Command implements ICommand {
        private Talent talent;

        public Command(final Talent talent) {
            this.talent = talent;
        }

        public Talent getTalent() {
            return talent;
        }
    }

    @Named("TalentPersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final TalentPersisterService persister;

        @Inject
        public CommandHandler(final TalentPersisterService service) {
            this.persister = service;
        }

        public CompletableFuture<Talent> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return persister.persist(command.getTalent());
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
