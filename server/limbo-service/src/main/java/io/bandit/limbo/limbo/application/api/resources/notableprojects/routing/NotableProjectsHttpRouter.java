package io.bandit.limbo.limbo.application.api.resources.notableprojects.routing;

import org.springframework.beans.factory.annotation.Value;
import javax.inject.Named;

@Named("NotableProjectsHttpRouter")
public class NotableProjectsHttpRouter {

    private static final String RESOURCE_NAME = "notable-projects";
    private static final String RESOURCE_ID = "{id}";

    public static final String NOTABLEPROJECTS_MANY_ROUTE = "/api/" + RESOURCE_NAME;
    public static final String NOTABLEPROJECTS_ONE_ROUTE = "/api/" + RESOURCE_NAME + "/" + RESOURCE_ID;
    public static final String NOTABLEPROJECTS_TALENT_ROUTE = NOTABLEPROJECTS_ONE_ROUTE + "/talents";

    public String getNotableProjectsRoute() {
        return NOTABLEPROJECTS_MANY_ROUTE;
    }

    public String getNotableProjectsRoute(final String id) {
        return NOTABLEPROJECTS_ONE_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getNotableProjectsTalentRoute(final String id) {
        return NOTABLEPROJECTS_TALENT_ROUTE.replace(RESOURCE_ID, id);
    }
}
