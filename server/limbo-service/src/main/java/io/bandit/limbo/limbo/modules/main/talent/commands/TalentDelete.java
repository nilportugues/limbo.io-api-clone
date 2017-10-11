package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentDeleted;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.main.talentprofile.event.TalentProfileDeleted;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.modules.main.talentrole.event.TalentRoleDeleted;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.event.CountryDeleted;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.city.event.CityDeleted;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.main.talenttitle.event.TalentTitleDeleted;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.main.talentexperience.event.TalentExperienceDeleted;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.main.worktype.event.WorkTypeDeleted;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.skills.event.SkillsDeleted;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.notableprojects.event.NotableProjectsDeleted;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.companytraits.event.CompanyTraitsDeleted;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.personaltraits.event.PersonalTraitsDeleted;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.main.socialnetworks.event.SocialNetworksDeleted;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.joboffer.event.JobOfferDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

public class TalentDelete {

    public static class Command implements ICommand {
         private String talentId;

         public Command(final String talentId) {
             this.talentId = talentId;
         }

         public String getTalentId() {
             return talentId;
        }
    }

    @Named("TalentDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final TalentRepository talentRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final TalentRepository talentRepository, final EventBus eventBus) {
            this.talentRepository = talentRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the Talent by id.
         */
        public CompletableFuture<Talent> handle(final Command command) {
        //public Mono<Talent> delete(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                final Talent talent = talentRepository.findOne(command.getTalentId());

                if (null != talent) {
                    this.removeDomainRelationships(talent);
                    this.removePersistenceRelationships(talent);
                    this.raiseDeletedEvent(talent);
                }

                return talent;
            });
        }

        private void removeDomainRelationships(final Talent talent) {
            talent.setTalentProfile(null);
            talent.setTalentRole(null);
            talent.setCountry(null);
            talent.setCity(null);
            talent.setTalentTitle(null);
            talent.setTalentExperience(null);
            talent.setWorkType(null);
            talent.setSkills(null);
            talent.setNotableProjects(null);
            talent.setCompanyTraits(null);
            talent.setPersonalTraits(null);
            talent.setSocialNetworks(null);
            talent.setJobOffers(null);
        }

        private void removePersistenceRelationships(final Talent talent) {
            talentRepository.save(talent);
            talentRepository.delete(talent.getId());
        }

        private void raiseDeletedEvent(final Talent talent) {
            eventBus.dispatch(new TalentDeleted(talent));
        }
    }

}
