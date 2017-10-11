package io.bandit.limbo.limbo.application.api.resources.talent.routing;

import org.springframework.beans.factory.annotation.Value;
import javax.inject.Named;

@Named("TalentHttpRouter")
public class TalentHttpRouter {

    private static final String RESOURCE_NAME = "talents";
    private static final String RESOURCE_ID = "{id}";

    public static final String TALENT_MANY_ROUTE = "/api/" + RESOURCE_NAME;
    public static final String TALENT_ONE_ROUTE = "/api/" + RESOURCE_NAME + "/" + RESOURCE_ID;
    public static final String TALENT_TALENTPROFILE_ROUTE = TALENT_ONE_ROUTE + "/talent-profiles";
    public static final String TALENT_TALENTROLE_ROUTE = TALENT_ONE_ROUTE + "/talent-roles";
    public static final String TALENT_COUNTRY_ROUTE = TALENT_ONE_ROUTE + "/countries";
    public static final String TALENT_CITY_ROUTE = TALENT_ONE_ROUTE + "/cities";
    public static final String TALENT_TALENTTITLE_ROUTE = TALENT_ONE_ROUTE + "/talent-titles";
    public static final String TALENT_TALENTEXPERIENCE_ROUTE = TALENT_ONE_ROUTE + "/talent-experiences";
    public static final String TALENT_WORKTYPE_ROUTE = TALENT_ONE_ROUTE + "/work-types";
    public static final String TALENT_SKILLS_ROUTE = TALENT_ONE_ROUTE + "/skills";
    public static final String TALENT_SKILLS_DELETE_BATCH_ROUTE = TALENT_ONE_ROUTE + "/skills/batch";
    public static final String TALENT_SKILLS_DELETE_ALL_ROUTE = TALENT_ONE_ROUTE + "/skills/all";
    public static final String TALENT_NOTABLEPROJECTS_ROUTE = TALENT_ONE_ROUTE + "/notable-projects";
    public static final String TALENT_NOTABLEPROJECTS_DELETE_BATCH_ROUTE = TALENT_ONE_ROUTE + "/notable-projects/batch";
    public static final String TALENT_NOTABLEPROJECTS_DELETE_ALL_ROUTE = TALENT_ONE_ROUTE + "/notable-projects/all";
    public static final String TALENT_COMPANYTRAITS_ROUTE = TALENT_ONE_ROUTE + "/company-traits";
    public static final String TALENT_COMPANYTRAITS_DELETE_BATCH_ROUTE = TALENT_ONE_ROUTE + "/company-traits/batch";
    public static final String TALENT_COMPANYTRAITS_DELETE_ALL_ROUTE = TALENT_ONE_ROUTE + "/company-traits/all";
    public static final String TALENT_PERSONALTRAITS_ROUTE = TALENT_ONE_ROUTE + "/personal-traits";
    public static final String TALENT_PERSONALTRAITS_DELETE_BATCH_ROUTE = TALENT_ONE_ROUTE + "/personal-traits/batch";
    public static final String TALENT_PERSONALTRAITS_DELETE_ALL_ROUTE = TALENT_ONE_ROUTE + "/personal-traits/all";
    public static final String TALENT_SOCIALNETWORKS_ROUTE = TALENT_ONE_ROUTE + "/social-networks";
    public static final String TALENT_SOCIALNETWORKS_DELETE_BATCH_ROUTE = TALENT_ONE_ROUTE + "/social-networks/batch";
    public static final String TALENT_SOCIALNETWORKS_DELETE_ALL_ROUTE = TALENT_ONE_ROUTE + "/social-networks/all";
    public static final String TALENT_JOBOFFER_ROUTE = TALENT_ONE_ROUTE + "/job-offers";
    public static final String TALENT_JOBOFFER_DELETE_BATCH_ROUTE = TALENT_ONE_ROUTE + "/job-offers/batch";
    public static final String TALENT_JOBOFFER_DELETE_ALL_ROUTE = TALENT_ONE_ROUTE + "/job-offers/all";

    public String getTalentsRoute() {
        return TALENT_MANY_ROUTE;
    }

    public String getTalentRoute(final String id) {
        return TALENT_ONE_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getTalentTalentProfileRoute(final String id) {
        return TALENT_TALENTPROFILE_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getTalentTalentRoleRoute(final String id) {
        return TALENT_TALENTROLE_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getTalentCountryRoute(final String id) {
        return TALENT_COUNTRY_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getTalentCityRoute(final String id) {
        return TALENT_CITY_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getTalentTalentTitleRoute(final String id) {
        return TALENT_TALENTTITLE_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getTalentTalentExperienceRoute(final String id) {
        return TALENT_TALENTEXPERIENCE_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getTalentWorkTypeRoute(final String id) {
        return TALENT_WORKTYPE_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getTalentSkillsRoute(final String id) {
        return TALENT_SKILLS_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getTalentNotableProjectsRoute(final String id) {
        return TALENT_NOTABLEPROJECTS_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getTalentCompanyTraitsRoute(final String id) {
        return TALENT_COMPANYTRAITS_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getTalentPersonalTraitsRoute(final String id) {
        return TALENT_PERSONALTRAITS_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getTalentSocialNetworksRoute(final String id) {
        return TALENT_SOCIALNETWORKS_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getTalentJobOfferRoute(final String id) {
        return TALENT_JOBOFFER_ROUTE.replace(RESOURCE_ID, id);
    }
}
