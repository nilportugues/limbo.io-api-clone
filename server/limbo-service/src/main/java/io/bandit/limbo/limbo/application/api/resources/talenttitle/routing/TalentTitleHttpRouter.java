package io.bandit.limbo.limbo.application.api.resources.talenttitle.routing;

import org.springframework.beans.factory.annotation.Value;
import javax.inject.Named;

@Named("TalentTitleHttpRouter")
public class TalentTitleHttpRouter {

    private static final String RESOURCE_NAME = "talent-titles";
    private static final String RESOURCE_ID = "{id}";

    public static final String TALENTTITLE_MANY_ROUTE = "/api/" + RESOURCE_NAME;
    public static final String TALENTTITLE_ONE_ROUTE = "/api/" + RESOURCE_NAME + "/" + RESOURCE_ID;

    public String getTalentTitlesRoute() {
        return TALENTTITLE_MANY_ROUTE;
    }

    public String getTalentTitleRoute(final String id) {
        return TALENTTITLE_ONE_ROUTE.replace(RESOURCE_ID, id);
    }
}
