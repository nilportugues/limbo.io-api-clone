package io.bandit.limbo.limbo.application.api.resources.talent.metrics;

import io.bandit.limbo.limbo.modules.shared.metrics.Metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

@Named
public class TalentApiMetrics extends Metrics {

    private static final String TALENT = ".talent";
    private static final String TALENT_TALENTPROFILE = ".talent_talent_profile";
    private static final String TALENT_TALENTROLE = ".talent_talent_role";
    private static final String TALENT_COUNTRY = ".talent_country";
    private static final String TALENT_CITY = ".talent_city";
    private static final String TALENT_TALENTTITLE = ".talent_talent_title";
    private static final String TALENT_TALENTEXPERIENCE = ".talent_talent_experience";
    private static final String TALENT_WORKTYPE = ".talent_work_type";
    private static final String TALENT_SKILLS = ".talent_skills";
    private static final String TALENT_NOTABLEPROJECTS = ".talent_notable_projects";
    private static final String TALENT_COMPANYTRAITS = ".talent_company_traits";
    private static final String TALENT_PERSONALTRAITS = ".talent_personal_traits";
    private static final String TALENT_SOCIALNETWORKS = ".talent_social_networks";
    private static final String TALENT_JOBOFFER = ".talent_job_offer";

    private static Counter talentErrorCounter;
    private static Counter talentSuccessCounter;
    private static Counter talentNotFoundCounter;
    private static Counter talentCreatedCounter;
    private static Counter talentUpdatedCounter;
    private static Counter talentDeletedCounter;

    private static Counter talentTalentProfileErrorCounter;
    private static Counter talentTalentProfileSuccessCounter;
    private static Counter talentTalentProfileNotFoundCounter;
    private static Counter talentTalentProfileCreatedCounter;
    private static Counter talentTalentProfileUpdatedCounter;
    private static Counter talentTalentProfileDeletedCounter;

    private static Counter talentTalentRoleErrorCounter;
    private static Counter talentTalentRoleSuccessCounter;
    private static Counter talentTalentRoleNotFoundCounter;
    private static Counter talentTalentRoleCreatedCounter;
    private static Counter talentTalentRoleUpdatedCounter;
    private static Counter talentTalentRoleDeletedCounter;

    private static Counter talentCountryErrorCounter;
    private static Counter talentCountrySuccessCounter;
    private static Counter talentCountryNotFoundCounter;
    private static Counter talentCountryCreatedCounter;
    private static Counter talentCountryUpdatedCounter;
    private static Counter talentCountryDeletedCounter;

    private static Counter talentCityErrorCounter;
    private static Counter talentCitySuccessCounter;
    private static Counter talentCityNotFoundCounter;
    private static Counter talentCityCreatedCounter;
    private static Counter talentCityUpdatedCounter;
    private static Counter talentCityDeletedCounter;

    private static Counter talentTalentTitleErrorCounter;
    private static Counter talentTalentTitleSuccessCounter;
    private static Counter talentTalentTitleNotFoundCounter;
    private static Counter talentTalentTitleCreatedCounter;
    private static Counter talentTalentTitleUpdatedCounter;
    private static Counter talentTalentTitleDeletedCounter;

    private static Counter talentTalentExperienceErrorCounter;
    private static Counter talentTalentExperienceSuccessCounter;
    private static Counter talentTalentExperienceNotFoundCounter;
    private static Counter talentTalentExperienceCreatedCounter;
    private static Counter talentTalentExperienceUpdatedCounter;
    private static Counter talentTalentExperienceDeletedCounter;

    private static Counter talentWorkTypeErrorCounter;
    private static Counter talentWorkTypeSuccessCounter;
    private static Counter talentWorkTypeNotFoundCounter;
    private static Counter talentWorkTypeCreatedCounter;
    private static Counter talentWorkTypeUpdatedCounter;
    private static Counter talentWorkTypeDeletedCounter;

    private static Counter talentSkillsErrorCounter;
    private static Counter talentSkillsSuccessCounter;
    private static Counter talentSkillsNotFoundCounter;
    private static Counter talentSkillsCreatedCounter;
    private static Counter talentSkillsUpdatedCounter;
    private static Counter talentSkillsDeletedCounter;

    private static Counter talentNotableProjectsErrorCounter;
    private static Counter talentNotableProjectsSuccessCounter;
    private static Counter talentNotableProjectsNotFoundCounter;
    private static Counter talentNotableProjectsCreatedCounter;
    private static Counter talentNotableProjectsUpdatedCounter;
    private static Counter talentNotableProjectsDeletedCounter;

    private static Counter talentCompanyTraitsErrorCounter;
    private static Counter talentCompanyTraitsSuccessCounter;
    private static Counter talentCompanyTraitsNotFoundCounter;
    private static Counter talentCompanyTraitsCreatedCounter;
    private static Counter talentCompanyTraitsUpdatedCounter;
    private static Counter talentCompanyTraitsDeletedCounter;

    private static Counter talentPersonalTraitsErrorCounter;
    private static Counter talentPersonalTraitsSuccessCounter;
    private static Counter talentPersonalTraitsNotFoundCounter;
    private static Counter talentPersonalTraitsCreatedCounter;
    private static Counter talentPersonalTraitsUpdatedCounter;
    private static Counter talentPersonalTraitsDeletedCounter;

    private static Counter talentSocialNetworksErrorCounter;
    private static Counter talentSocialNetworksSuccessCounter;
    private static Counter talentSocialNetworksNotFoundCounter;
    private static Counter talentSocialNetworksCreatedCounter;
    private static Counter talentSocialNetworksUpdatedCounter;
    private static Counter talentSocialNetworksDeletedCounter;

    private static Counter talentJobOfferErrorCounter;
    private static Counter talentJobOfferSuccessCounter;
    private static Counter talentJobOfferNotFoundCounter;
    private static Counter talentJobOfferCreatedCounter;
    private static Counter talentJobOfferUpdatedCounter;
    private static Counter talentJobOfferDeletedCounter;

    public TalentApiMetrics(final MeterRegistry registry) {
        talentErrorCounter = registry.counter(API_ERROR + TALENT);
        talentSuccessCounter = registry.counter(API_FETCH + TALENT);
        talentNotFoundCounter = registry.counter(API_NOTFOUND + TALENT);
        talentCreatedCounter = registry.counter(API_CREATED + TALENT);
        talentUpdatedCounter = registry.counter(API_UPDATED + TALENT);
        talentDeletedCounter = registry.counter(API_DELETED + TALENT);

        talentTalentProfileErrorCounter= registry.counter(API_ERROR + TALENT_TALENTPROFILE);
        talentTalentProfileSuccessCounter = registry.counter(API_FETCH + TALENT_TALENTPROFILE);
        talentTalentProfileCreatedCounter = registry.counter(API_CREATED + TALENT_TALENTPROFILE);
        talentTalentProfileUpdatedCounter = registry.counter(API_UPDATED + TALENT_TALENTPROFILE);
        talentTalentProfileDeletedCounter = registry.counter(API_DELETED + TALENT_TALENTPROFILE);
        talentTalentProfileNotFoundCounter = registry.counter(API_NOTFOUND + TALENT_TALENTPROFILE);

        talentTalentRoleErrorCounter= registry.counter(API_ERROR + TALENT_TALENTROLE);
        talentTalentRoleSuccessCounter = registry.counter(API_FETCH + TALENT_TALENTROLE);
        talentTalentRoleCreatedCounter = registry.counter(API_CREATED + TALENT_TALENTROLE);
        talentTalentRoleUpdatedCounter = registry.counter(API_UPDATED + TALENT_TALENTROLE);
        talentTalentRoleDeletedCounter = registry.counter(API_DELETED + TALENT_TALENTROLE);
        talentTalentRoleNotFoundCounter = registry.counter(API_NOTFOUND + TALENT_TALENTROLE);

        talentCountryErrorCounter= registry.counter(API_ERROR + TALENT_COUNTRY);
        talentCountrySuccessCounter = registry.counter(API_FETCH + TALENT_COUNTRY);
        talentCountryCreatedCounter = registry.counter(API_CREATED + TALENT_COUNTRY);
        talentCountryUpdatedCounter = registry.counter(API_UPDATED + TALENT_COUNTRY);
        talentCountryDeletedCounter = registry.counter(API_DELETED + TALENT_COUNTRY);
        talentCountryNotFoundCounter = registry.counter(API_NOTFOUND + TALENT_COUNTRY);

        talentCityErrorCounter= registry.counter(API_ERROR + TALENT_CITY);
        talentCitySuccessCounter = registry.counter(API_FETCH + TALENT_CITY);
        talentCityCreatedCounter = registry.counter(API_CREATED + TALENT_CITY);
        talentCityUpdatedCounter = registry.counter(API_UPDATED + TALENT_CITY);
        talentCityDeletedCounter = registry.counter(API_DELETED + TALENT_CITY);
        talentCityNotFoundCounter = registry.counter(API_NOTFOUND + TALENT_CITY);

        talentTalentTitleErrorCounter= registry.counter(API_ERROR + TALENT_TALENTTITLE);
        talentTalentTitleSuccessCounter = registry.counter(API_FETCH + TALENT_TALENTTITLE);
        talentTalentTitleCreatedCounter = registry.counter(API_CREATED + TALENT_TALENTTITLE);
        talentTalentTitleUpdatedCounter = registry.counter(API_UPDATED + TALENT_TALENTTITLE);
        talentTalentTitleDeletedCounter = registry.counter(API_DELETED + TALENT_TALENTTITLE);
        talentTalentTitleNotFoundCounter = registry.counter(API_NOTFOUND + TALENT_TALENTTITLE);

        talentTalentExperienceErrorCounter= registry.counter(API_ERROR + TALENT_TALENTEXPERIENCE);
        talentTalentExperienceSuccessCounter = registry.counter(API_FETCH + TALENT_TALENTEXPERIENCE);
        talentTalentExperienceCreatedCounter = registry.counter(API_CREATED + TALENT_TALENTEXPERIENCE);
        talentTalentExperienceUpdatedCounter = registry.counter(API_UPDATED + TALENT_TALENTEXPERIENCE);
        talentTalentExperienceDeletedCounter = registry.counter(API_DELETED + TALENT_TALENTEXPERIENCE);
        talentTalentExperienceNotFoundCounter = registry.counter(API_NOTFOUND + TALENT_TALENTEXPERIENCE);

        talentWorkTypeErrorCounter= registry.counter(API_ERROR + TALENT_WORKTYPE);
        talentWorkTypeSuccessCounter = registry.counter(API_FETCH + TALENT_WORKTYPE);
        talentWorkTypeCreatedCounter = registry.counter(API_CREATED + TALENT_WORKTYPE);
        talentWorkTypeUpdatedCounter = registry.counter(API_UPDATED + TALENT_WORKTYPE);
        talentWorkTypeDeletedCounter = registry.counter(API_DELETED + TALENT_WORKTYPE);
        talentWorkTypeNotFoundCounter = registry.counter(API_NOTFOUND + TALENT_WORKTYPE);

        talentSkillsErrorCounter= registry.counter(API_ERROR + TALENT_SKILLS);
        talentSkillsSuccessCounter = registry.counter(API_FETCH + TALENT_SKILLS);
        talentSkillsCreatedCounter = registry.counter(API_CREATED + TALENT_SKILLS);
        talentSkillsUpdatedCounter = registry.counter(API_UPDATED + TALENT_SKILLS);
        talentSkillsDeletedCounter = registry.counter(API_DELETED + TALENT_SKILLS);
        talentSkillsNotFoundCounter = registry.counter(API_NOTFOUND + TALENT_SKILLS);

        talentNotableProjectsErrorCounter= registry.counter(API_ERROR + TALENT_NOTABLEPROJECTS);
        talentNotableProjectsSuccessCounter = registry.counter(API_FETCH + TALENT_NOTABLEPROJECTS);
        talentNotableProjectsCreatedCounter = registry.counter(API_CREATED + TALENT_NOTABLEPROJECTS);
        talentNotableProjectsUpdatedCounter = registry.counter(API_UPDATED + TALENT_NOTABLEPROJECTS);
        talentNotableProjectsDeletedCounter = registry.counter(API_DELETED + TALENT_NOTABLEPROJECTS);
        talentNotableProjectsNotFoundCounter = registry.counter(API_NOTFOUND + TALENT_NOTABLEPROJECTS);

        talentCompanyTraitsErrorCounter= registry.counter(API_ERROR + TALENT_COMPANYTRAITS);
        talentCompanyTraitsSuccessCounter = registry.counter(API_FETCH + TALENT_COMPANYTRAITS);
        talentCompanyTraitsCreatedCounter = registry.counter(API_CREATED + TALENT_COMPANYTRAITS);
        talentCompanyTraitsUpdatedCounter = registry.counter(API_UPDATED + TALENT_COMPANYTRAITS);
        talentCompanyTraitsDeletedCounter = registry.counter(API_DELETED + TALENT_COMPANYTRAITS);
        talentCompanyTraitsNotFoundCounter = registry.counter(API_NOTFOUND + TALENT_COMPANYTRAITS);

        talentPersonalTraitsErrorCounter= registry.counter(API_ERROR + TALENT_PERSONALTRAITS);
        talentPersonalTraitsSuccessCounter = registry.counter(API_FETCH + TALENT_PERSONALTRAITS);
        talentPersonalTraitsCreatedCounter = registry.counter(API_CREATED + TALENT_PERSONALTRAITS);
        talentPersonalTraitsUpdatedCounter = registry.counter(API_UPDATED + TALENT_PERSONALTRAITS);
        talentPersonalTraitsDeletedCounter = registry.counter(API_DELETED + TALENT_PERSONALTRAITS);
        talentPersonalTraitsNotFoundCounter = registry.counter(API_NOTFOUND + TALENT_PERSONALTRAITS);

        talentSocialNetworksErrorCounter= registry.counter(API_ERROR + TALENT_SOCIALNETWORKS);
        talentSocialNetworksSuccessCounter = registry.counter(API_FETCH + TALENT_SOCIALNETWORKS);
        talentSocialNetworksCreatedCounter = registry.counter(API_CREATED + TALENT_SOCIALNETWORKS);
        talentSocialNetworksUpdatedCounter = registry.counter(API_UPDATED + TALENT_SOCIALNETWORKS);
        talentSocialNetworksDeletedCounter = registry.counter(API_DELETED + TALENT_SOCIALNETWORKS);
        talentSocialNetworksNotFoundCounter = registry.counter(API_NOTFOUND + TALENT_SOCIALNETWORKS);

        talentJobOfferErrorCounter= registry.counter(API_ERROR + TALENT_JOBOFFER);
        talentJobOfferSuccessCounter = registry.counter(API_FETCH + TALENT_JOBOFFER);
        talentJobOfferCreatedCounter = registry.counter(API_CREATED + TALENT_JOBOFFER);
        talentJobOfferUpdatedCounter = registry.counter(API_UPDATED + TALENT_JOBOFFER);
        talentJobOfferDeletedCounter = registry.counter(API_DELETED + TALENT_JOBOFFER);
        talentJobOfferNotFoundCounter = registry.counter(API_NOTFOUND + TALENT_JOBOFFER);

    }

    public void incrementTalentError() {
        increment(talentErrorCounter);
    }

    public void incrementTalentSuccess() {
        increment(talentSuccessCounter);
    }

    public void incrementTalentNotFound() {
        increment(talentNotFoundCounter);
    }

    public void incrementTalentCreated() {
        increment(talentCreatedCounter);
    }

    public void incrementTalentUpdated() {
        increment(talentUpdatedCounter);
    }

    public void incrementTalentDeleted() {
        increment(talentDeletedCounter);
    }

    public void incrementTalentTalentProfileError() {
        increment(talentTalentProfileErrorCounter);
    }

    public void incrementTalentTalentProfileSuccess() {
        increment(talentTalentProfileSuccessCounter);
    }

    public void incrementTalentTalentProfileNotFound() {
        increment(talentTalentProfileNotFoundCounter);
    }

    public void incrementTalentTalentProfileCreated() {
        increment(talentTalentProfileCreatedCounter);
    }

    public void incrementTalentTalentProfileUpdated() {
        increment(talentTalentProfileUpdatedCounter);
    }

    public void incrementTalentTalentProfileDeleted() {
        increment(talentTalentProfileDeletedCounter);
    }

    public void incrementTalentTalentRoleError() {
        increment(talentTalentRoleErrorCounter);
    }

    public void incrementTalentTalentRoleSuccess() {
        increment(talentTalentRoleSuccessCounter);
    }

    public void incrementTalentTalentRoleNotFound() {
        increment(talentTalentRoleNotFoundCounter);
    }

    public void incrementTalentTalentRoleCreated() {
        increment(talentTalentRoleCreatedCounter);
    }

    public void incrementTalentTalentRoleUpdated() {
        increment(talentTalentRoleUpdatedCounter);
    }

    public void incrementTalentTalentRoleDeleted() {
        increment(talentTalentRoleDeletedCounter);
    }

    public void incrementTalentCountryError() {
        increment(talentCountryErrorCounter);
    }

    public void incrementTalentCountrySuccess() {
        increment(talentCountrySuccessCounter);
    }

    public void incrementTalentCountryNotFound() {
        increment(talentCountryNotFoundCounter);
    }

    public void incrementTalentCountryCreated() {
        increment(talentCountryCreatedCounter);
    }

    public void incrementTalentCountryUpdated() {
        increment(talentCountryUpdatedCounter);
    }

    public void incrementTalentCountryDeleted() {
        increment(talentCountryDeletedCounter);
    }

    public void incrementTalentCityError() {
        increment(talentCityErrorCounter);
    }

    public void incrementTalentCitySuccess() {
        increment(talentCitySuccessCounter);
    }

    public void incrementTalentCityNotFound() {
        increment(talentCityNotFoundCounter);
    }

    public void incrementTalentCityCreated() {
        increment(talentCityCreatedCounter);
    }

    public void incrementTalentCityUpdated() {
        increment(talentCityUpdatedCounter);
    }

    public void incrementTalentCityDeleted() {
        increment(talentCityDeletedCounter);
    }

    public void incrementTalentTalentTitleError() {
        increment(talentTalentTitleErrorCounter);
    }

    public void incrementTalentTalentTitleSuccess() {
        increment(talentTalentTitleSuccessCounter);
    }

    public void incrementTalentTalentTitleNotFound() {
        increment(talentTalentTitleNotFoundCounter);
    }

    public void incrementTalentTalentTitleCreated() {
        increment(talentTalentTitleCreatedCounter);
    }

    public void incrementTalentTalentTitleUpdated() {
        increment(talentTalentTitleUpdatedCounter);
    }

    public void incrementTalentTalentTitleDeleted() {
        increment(talentTalentTitleDeletedCounter);
    }

    public void incrementTalentTalentExperienceError() {
        increment(talentTalentExperienceErrorCounter);
    }

    public void incrementTalentTalentExperienceSuccess() {
        increment(talentTalentExperienceSuccessCounter);
    }

    public void incrementTalentTalentExperienceNotFound() {
        increment(talentTalentExperienceNotFoundCounter);
    }

    public void incrementTalentTalentExperienceCreated() {
        increment(talentTalentExperienceCreatedCounter);
    }

    public void incrementTalentTalentExperienceUpdated() {
        increment(talentTalentExperienceUpdatedCounter);
    }

    public void incrementTalentTalentExperienceDeleted() {
        increment(talentTalentExperienceDeletedCounter);
    }

    public void incrementTalentWorkTypeError() {
        increment(talentWorkTypeErrorCounter);
    }

    public void incrementTalentWorkTypeSuccess() {
        increment(talentWorkTypeSuccessCounter);
    }

    public void incrementTalentWorkTypeNotFound() {
        increment(talentWorkTypeNotFoundCounter);
    }

    public void incrementTalentWorkTypeCreated() {
        increment(talentWorkTypeCreatedCounter);
    }

    public void incrementTalentWorkTypeUpdated() {
        increment(talentWorkTypeUpdatedCounter);
    }

    public void incrementTalentWorkTypeDeleted() {
        increment(talentWorkTypeDeletedCounter);
    }

    public void incrementTalentSkillsError() {
        increment(talentSkillsErrorCounter);
    }

    public void incrementTalentSkillsSuccess() {
        increment(talentSkillsSuccessCounter);
    }

    public void incrementTalentSkillsNotFound() {
        increment(talentSkillsNotFoundCounter);
    }

    public void incrementTalentSkillsCreated() {
        increment(talentSkillsCreatedCounter);
    }

    public void incrementTalentSkillsUpdated() {
        increment(talentSkillsUpdatedCounter);
    }

    public void incrementTalentSkillsDeleted() {
        increment(talentSkillsDeletedCounter);
    }

    public void incrementTalentNotableProjectsError() {
        increment(talentNotableProjectsErrorCounter);
    }

    public void incrementTalentNotableProjectsSuccess() {
        increment(talentNotableProjectsSuccessCounter);
    }

    public void incrementTalentNotableProjectsNotFound() {
        increment(talentNotableProjectsNotFoundCounter);
    }

    public void incrementTalentNotableProjectsCreated() {
        increment(talentNotableProjectsCreatedCounter);
    }

    public void incrementTalentNotableProjectsUpdated() {
        increment(talentNotableProjectsUpdatedCounter);
    }

    public void incrementTalentNotableProjectsDeleted() {
        increment(talentNotableProjectsDeletedCounter);
    }

    public void incrementTalentCompanyTraitsError() {
        increment(talentCompanyTraitsErrorCounter);
    }

    public void incrementTalentCompanyTraitsSuccess() {
        increment(talentCompanyTraitsSuccessCounter);
    }

    public void incrementTalentCompanyTraitsNotFound() {
        increment(talentCompanyTraitsNotFoundCounter);
    }

    public void incrementTalentCompanyTraitsCreated() {
        increment(talentCompanyTraitsCreatedCounter);
    }

    public void incrementTalentCompanyTraitsUpdated() {
        increment(talentCompanyTraitsUpdatedCounter);
    }

    public void incrementTalentCompanyTraitsDeleted() {
        increment(talentCompanyTraitsDeletedCounter);
    }

    public void incrementTalentPersonalTraitsError() {
        increment(talentPersonalTraitsErrorCounter);
    }

    public void incrementTalentPersonalTraitsSuccess() {
        increment(talentPersonalTraitsSuccessCounter);
    }

    public void incrementTalentPersonalTraitsNotFound() {
        increment(talentPersonalTraitsNotFoundCounter);
    }

    public void incrementTalentPersonalTraitsCreated() {
        increment(talentPersonalTraitsCreatedCounter);
    }

    public void incrementTalentPersonalTraitsUpdated() {
        increment(talentPersonalTraitsUpdatedCounter);
    }

    public void incrementTalentPersonalTraitsDeleted() {
        increment(talentPersonalTraitsDeletedCounter);
    }

    public void incrementTalentSocialNetworksError() {
        increment(talentSocialNetworksErrorCounter);
    }

    public void incrementTalentSocialNetworksSuccess() {
        increment(talentSocialNetworksSuccessCounter);
    }

    public void incrementTalentSocialNetworksNotFound() {
        increment(talentSocialNetworksNotFoundCounter);
    }

    public void incrementTalentSocialNetworksCreated() {
        increment(talentSocialNetworksCreatedCounter);
    }

    public void incrementTalentSocialNetworksUpdated() {
        increment(talentSocialNetworksUpdatedCounter);
    }

    public void incrementTalentSocialNetworksDeleted() {
        increment(talentSocialNetworksDeletedCounter);
    }

    public void incrementTalentJobOfferError() {
        increment(talentJobOfferErrorCounter);
    }

    public void incrementTalentJobOfferSuccess() {
        increment(talentJobOfferSuccessCounter);
    }

    public void incrementTalentJobOfferNotFound() {
        increment(talentJobOfferNotFoundCounter);
    }

    public void incrementTalentJobOfferCreated() {
        increment(talentJobOfferCreatedCounter);
    }

    public void incrementTalentJobOfferUpdated() {
        increment(talentJobOfferUpdatedCounter);
    }

    public void incrementTalentJobOfferDeleted() {
        increment(talentJobOfferDeletedCounter);
    }


    private void increment(final Counter counter) {
        CompletableFuture.runAsync(counter::increment);
    }
}
