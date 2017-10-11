package io.bandit.limbo.limbo.application.api.resources.talentrole.routing;

import org.springframework.beans.factory.annotation.Value;
import javax.inject.Named;

@Named("TalentRoleHttpRouter")
public class TalentRoleHttpRouter {

    private static final String RESOURCE_NAME = "talent-roles";
    private static final String RESOURCE_ID = "{id}";

    public static final String TALENTROLE_MANY_ROUTE = "/api/" + RESOURCE_NAME;
    public static final String TALENTROLE_ONE_ROUTE = "/api/" + RESOURCE_NAME + "/" + RESOURCE_ID;

    public String getTalentRolesRoute() {
        return TALENTROLE_MANY_ROUTE;
    }

    public String getTalentRoleRoute(final String id) {
        return TALENTROLE_ONE_ROUTE.replace(RESOURCE_ID, id);
    }
}
