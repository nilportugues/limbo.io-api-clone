package io.bandit.limbo.limbo.application.api.resources.socialnetworks.routing;

import org.springframework.beans.factory.annotation.Value;
import javax.inject.Named;

@Named("SocialNetworksHttpRouter")
public class SocialNetworksHttpRouter {

    private static final String RESOURCE_NAME = "social-networks";
    private static final String RESOURCE_ID = "{id}";

    public static final String SOCIALNETWORKS_MANY_ROUTE = "/api/" + RESOURCE_NAME;
    public static final String SOCIALNETWORKS_ONE_ROUTE = "/api/" + RESOURCE_NAME + "/" + RESOURCE_ID;
    public static final String SOCIALNETWORKS_TALENT_ROUTE = SOCIALNETWORKS_ONE_ROUTE + "/talents";

    public String getSocialNetworksRoute() {
        return SOCIALNETWORKS_MANY_ROUTE;
    }

    public String getSocialNetworksRoute(final String id) {
        return SOCIALNETWORKS_ONE_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getSocialNetworksTalentRoute(final String id) {
        return SOCIALNETWORKS_TALENT_ROUTE.replace(RESOURCE_ID, id);
    }
}
