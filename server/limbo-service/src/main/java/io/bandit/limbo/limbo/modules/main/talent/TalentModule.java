package io.bandit.limbo.limbo.modules.main.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.EventRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.QueryRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.CommandRegistry;
import io.bandit.limbo.limbo.modules.main.talent.commands.*;
import io.bandit.limbo.limbo.modules.main.talent.queries.*;
import io.bandit.limbo.limbo.modules.main.talent.event.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TalentModule {

    public TalentModule(final CommandRegistry commandRegistry, 
                                    final QueryRegistry queryRegistry, 
                                    final EventRegistry eventRegistry) {
        
        registerCommandHandlers(commandRegistry);
        registerQueryHandlers(queryRegistry);
        registerEventHandlers(eventRegistry);
    }

    /**
     * A Command must be mapped to a CommandHandler.
     */
    private void registerCommandHandlers(final CommandRegistry registry) {
        registry.register(TalentPersist.Command.class, "TalentPersist.CommandHandler");
        registry.register(TalentDelete.Command.class, "TalentDelete.CommandHandler");
        registry.register(TalentTalentProfilePersist.Command.class, "TalentTalentProfilePersist.CommandHandler");
        registry.register(TalentTalentProfileDelete.Command.class, "TalentTalentProfileDelete.CommandHandler");
        registry.register(TalentTalentRolePersist.Command.class, "TalentTalentRolePersist.CommandHandler");
        registry.register(TalentTalentRoleDelete.Command.class, "TalentTalentRoleDelete.CommandHandler");
        registry.register(TalentCountryPersist.Command.class, "TalentCountryPersist.CommandHandler");
        registry.register(TalentCountryDelete.Command.class, "TalentCountryDelete.CommandHandler");
        registry.register(TalentCityPersist.Command.class, "TalentCityPersist.CommandHandler");
        registry.register(TalentCityDelete.Command.class, "TalentCityDelete.CommandHandler");
        registry.register(TalentTalentTitlePersist.Command.class, "TalentTalentTitlePersist.CommandHandler");
        registry.register(TalentTalentTitleDelete.Command.class, "TalentTalentTitleDelete.CommandHandler");
        registry.register(TalentTalentExperiencePersist.Command.class, "TalentTalentExperiencePersist.CommandHandler");
        registry.register(TalentTalentExperienceDelete.Command.class, "TalentTalentExperienceDelete.CommandHandler");
        registry.register(TalentWorkTypePersist.Command.class, "TalentWorkTypePersist.CommandHandler");
        registry.register(TalentWorkTypeDelete.Command.class, "TalentWorkTypeDelete.CommandHandler");
        registry.register(TalentSkillsPersist.Command.class, "TalentSkillsPersist.CommandHandler");
        registry.register(TalentSkillsDelete.Command.class, "TalentSkillsDelete.CommandHandler");
        registry.register(TalentSkillsDeleteMany.Command.class, "TalentSkillsDeleteMany.CommandHandler");
        //registry.register(TalentSkillsDeleteMany.Command.class, "TalentSkillsDeleteMany.CommandHandler");
        registry.register(TalentNotableProjectsPersist.Command.class, "TalentNotableProjectsPersist.CommandHandler");
        registry.register(TalentNotableProjectsDelete.Command.class, "TalentNotableProjectsDelete.CommandHandler");
        registry.register(TalentNotableProjectsDeleteMany.Command.class, "TalentNotableProjectsDeleteMany.CommandHandler");
        //registry.register(TalentNotableProjectsDeleteMany.Command.class, "TalentNotableProjectsDeleteMany.CommandHandler");
        registry.register(TalentCompanyTraitsPersist.Command.class, "TalentCompanyTraitsPersist.CommandHandler");
        registry.register(TalentCompanyTraitsDelete.Command.class, "TalentCompanyTraitsDelete.CommandHandler");
        registry.register(TalentCompanyTraitsDeleteMany.Command.class, "TalentCompanyTraitsDeleteMany.CommandHandler");
        //registry.register(TalentCompanyTraitsDeleteMany.Command.class, "TalentCompanyTraitsDeleteMany.CommandHandler");
        registry.register(TalentPersonalTraitsPersist.Command.class, "TalentPersonalTraitsPersist.CommandHandler");
        registry.register(TalentPersonalTraitsDelete.Command.class, "TalentPersonalTraitsDelete.CommandHandler");
        registry.register(TalentPersonalTraitsDeleteMany.Command.class, "TalentPersonalTraitsDeleteMany.CommandHandler");
        //registry.register(TalentPersonalTraitsDeleteMany.Command.class, "TalentPersonalTraitsDeleteMany.CommandHandler");
        registry.register(TalentSocialNetworksPersist.Command.class, "TalentSocialNetworksPersist.CommandHandler");
        registry.register(TalentSocialNetworksDelete.Command.class, "TalentSocialNetworksDelete.CommandHandler");
        registry.register(TalentSocialNetworksDeleteMany.Command.class, "TalentSocialNetworksDeleteMany.CommandHandler");
        //registry.register(TalentSocialNetworksDeleteMany.Command.class, "TalentSocialNetworksDeleteMany.CommandHandler");
        registry.register(TalentJobOfferPersist.Command.class, "TalentJobOfferPersist.CommandHandler");
        registry.register(TalentJobOfferDelete.Command.class, "TalentJobOfferDelete.CommandHandler");
        registry.register(TalentJobOfferDeleteMany.Command.class, "TalentJobOfferDeleteMany.CommandHandler");
        //registry.register(TalentJobOfferDeleteMany.Command.class, "TalentJobOfferDeleteMany.CommandHandler");
    }

    /**
     * A Query must be mapped to a QueryHandler.
     */
    private void registerQueryHandlers(final QueryRegistry registry) {
        registry.register(GetTalent.Query.class, "GetTalent.QueryHandler");
        registry.register(TalentList.Query.class, "TalentList.QueryHandler");
        registry.register(TalentPaginated.Query.class, "TalentPaginated.QueryHandler");
        registry.register(TalentTalentProfile.Query.class, "TalentTalentProfile.QueryHandler");
        registry.register(TalentTalentRole.Query.class, "TalentTalentRole.QueryHandler");
        registry.register(TalentCountry.Query.class, "TalentCountry.QueryHandler");
        registry.register(TalentCity.Query.class, "TalentCity.QueryHandler");
        registry.register(TalentTalentTitle.Query.class, "TalentTalentTitle.QueryHandler");
        registry.register(TalentTalentExperience.Query.class, "TalentTalentExperience.QueryHandler");
        registry.register(TalentWorkType.Query.class, "TalentWorkType.QueryHandler");
        registry.register(TalentSkills.Query.class, "TalentSkills.QueryHandler");
        registry.register(TalentNotableProjects.Query.class, "TalentNotableProjects.QueryHandler");
        registry.register(TalentCompanyTraits.Query.class, "TalentCompanyTraits.QueryHandler");
        registry.register(TalentPersonalTraits.Query.class, "TalentPersonalTraits.QueryHandler");
        registry.register(TalentSocialNetworks.Query.class, "TalentSocialNetworks.QueryHandler");
        registry.register(TalentJobOffer.Query.class, "TalentJobOffer.QueryHandler");
    }

    /**
     * An Event may be registered to more than one Handler.
     *
     * It is good practive to have more than one ReadModel in over to cover all
     * your business needs. One just won't cut it.
     *
     */
    private void registerEventHandlers(final EventRegistry registry) {

        registry.register(TalentCreated.class, "TalentCreated.ReadModelHandler");
        registry.register(TalentUpdated.class, "TalentUpdated.ReadModelHandler");
        registry.register(TalentDeleted.class, "TalentDeleted.ReadModelHandler");

        //Talent properties
        registry.register(TalentEmailChanged.class, "TalentEmailChanged.Handler");
        registry.register(TalentPasswordChanged.class, "TalentPasswordChanged.Handler");

        //Talent's TalentProfile events
        registry.register(TalentTalentProfileChanged.class, "TalentTalentProfileChanged.ReadModelHandler");
        registry.register(TalentTalentProfileRemoved.class, "TalentTalentProfileRemoved.ReadModelHandler");
        
        //Talent's TalentRole events
        registry.register(TalentTalentRoleChanged.class, "TalentTalentRoleChanged.ReadModelHandler");
        registry.register(TalentTalentRoleRemoved.class, "TalentTalentRoleRemoved.ReadModelHandler");
        
        //Talent's Country events
        registry.register(TalentCountryChanged.class, "TalentCountryChanged.ReadModelHandler");
        registry.register(TalentCountryRemoved.class, "TalentCountryRemoved.ReadModelHandler");
        
        //Talent's City events
        registry.register(TalentCityChanged.class, "TalentCityChanged.ReadModelHandler");
        registry.register(TalentCityRemoved.class, "TalentCityRemoved.ReadModelHandler");
        
        //Talent's TalentTitle events
        registry.register(TalentTalentTitleChanged.class, "TalentTalentTitleChanged.ReadModelHandler");
        registry.register(TalentTalentTitleRemoved.class, "TalentTalentTitleRemoved.ReadModelHandler");
        
        //Talent's TalentExperience events
        registry.register(TalentTalentExperienceChanged.class, "TalentTalentExperienceChanged.ReadModelHandler");
        registry.register(TalentTalentExperienceRemoved.class, "TalentTalentExperienceRemoved.ReadModelHandler");
        
        //Talent's WorkType events
        registry.register(TalentWorkTypeChanged.class, "TalentWorkTypeChanged.ReadModelHandler");
        registry.register(TalentWorkTypeRemoved.class, "TalentWorkTypeRemoved.ReadModelHandler");
        
        //Talent's Skills events
        registry.register(TalentSkillsAdded.class, "TalentSkillsAdded.ReadModelHandler");
        registry.register(TalentSkillsRemoved.class, "TalentSkillsRemoved.ReadModelHandler");

        //Talent's NotableProjects events
        registry.register(TalentNotableProjectsAdded.class, "TalentNotableProjectsAdded.ReadModelHandler");
        registry.register(TalentNotableProjectsRemoved.class, "TalentNotableProjectsRemoved.ReadModelHandler");

        //Talent's CompanyTraits events
        registry.register(TalentCompanyTraitsAdded.class, "TalentCompanyTraitsAdded.ReadModelHandler");
        registry.register(TalentCompanyTraitsRemoved.class, "TalentCompanyTraitsRemoved.ReadModelHandler");

        //Talent's PersonalTraits events
        registry.register(TalentPersonalTraitsAdded.class, "TalentPersonalTraitsAdded.ReadModelHandler");
        registry.register(TalentPersonalTraitsRemoved.class, "TalentPersonalTraitsRemoved.ReadModelHandler");

        //Talent's SocialNetworks events
        registry.register(TalentSocialNetworksAdded.class, "TalentSocialNetworksAdded.ReadModelHandler");
        registry.register(TalentSocialNetworksRemoved.class, "TalentSocialNetworksRemoved.ReadModelHandler");

        //Talent's JobOffer events
        registry.register(TalentJobOfferAdded.class, "TalentJobOfferAdded.ReadModelHandler");
        registry.register(TalentJobOfferRemoved.class, "TalentJobOfferRemoved.ReadModelHandler");
    }

}
