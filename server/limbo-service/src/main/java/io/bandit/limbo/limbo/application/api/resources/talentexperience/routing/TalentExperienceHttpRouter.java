package io.bandit.limbo.limbo.application.api.resources.talentexperience.routing;

import org.springframework.beans.factory.annotation.Value;
import javax.inject.Named;

@Named("TalentExperienceHttpRouter")
public class TalentExperienceHttpRouter {

    private static final String RESOURCE_NAME = "talent-experiences";
    private static final String RESOURCE_ID = "{id}";

    public static final String TALENTEXPERIENCE_MANY_ROUTE = "/api/" + RESOURCE_NAME;
    public static final String TALENTEXPERIENCE_ONE_ROUTE = "/api/" + RESOURCE_NAME + "/" + RESOURCE_ID;

    public String getTalentExperiencesRoute() {
        return TALENTEXPERIENCE_MANY_ROUTE;
    }

    public String getTalentExperienceRoute(final String id) {
        return TALENTEXPERIENCE_ONE_ROUTE.replace(RESOURCE_ID, id);
    }
}
