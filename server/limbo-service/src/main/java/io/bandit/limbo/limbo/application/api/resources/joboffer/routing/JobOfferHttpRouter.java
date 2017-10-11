package io.bandit.limbo.limbo.application.api.resources.joboffer.routing;

import org.springframework.beans.factory.annotation.Value;
import javax.inject.Named;

@Named("JobOfferHttpRouter")
public class JobOfferHttpRouter {

    private static final String RESOURCE_NAME = "job-offers";
    private static final String RESOURCE_ID = "{id}";

    public static final String JOBOFFER_MANY_ROUTE = "/api/" + RESOURCE_NAME;
    public static final String JOBOFFER_ONE_ROUTE = "/api/" + RESOURCE_NAME + "/" + RESOURCE_ID;
    public static final String JOBOFFER_TALENT_ROUTE = JOBOFFER_ONE_ROUTE + "/talents";

    public String getJobOffersRoute() {
        return JOBOFFER_MANY_ROUTE;
    }

    public String getJobOfferRoute(final String id) {
        return JOBOFFER_ONE_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getJobOfferTalentRoute(final String id) {
        return JOBOFFER_TALENT_ROUTE.replace(RESOURCE_ID, id);
    }
}
