package io.bandit.limbo.limbo.application.api.resources.worktype.routing;

import org.springframework.beans.factory.annotation.Value;
import javax.inject.Named;

@Named("WorkTypeHttpRouter")
public class WorkTypeHttpRouter {

    private static final String RESOURCE_NAME = "work-types";
    private static final String RESOURCE_ID = "{id}";

    public static final String WORKTYPE_MANY_ROUTE = "/api/" + RESOURCE_NAME;
    public static final String WORKTYPE_ONE_ROUTE = "/api/" + RESOURCE_NAME + "/" + RESOURCE_ID;

    public String getWorkTypesRoute() {
        return WORKTYPE_MANY_ROUTE;
    }

    public String getWorkTypeRoute(final String id) {
        return WORKTYPE_ONE_ROUTE.replace(RESOURCE_ID, id);
    }
}
