package io.bandit.limbo.limbo.application.api.resources.talentprofile.routing;

import org.springframework.beans.factory.annotation.Value;
import javax.inject.Named;

@Named("TalentProfileHttpRouter")
public class TalentProfileHttpRouter {

    private static final String RESOURCE_NAME = "talent-profiles";
    private static final String RESOURCE_ID = "{id}";

    public static final String TALENTPROFILE_MANY_ROUTE = "/api/" + RESOURCE_NAME;
    public static final String TALENTPROFILE_ONE_ROUTE = "/api/" + RESOURCE_NAME + "/" + RESOURCE_ID;

    public String getTalentProfilesRoute() {
        return TALENTPROFILE_MANY_ROUTE;
    }

    public String getTalentProfileRoute(final String id) {
        return TALENTPROFILE_ONE_ROUTE.replace(RESOURCE_ID, id);
    }
}
