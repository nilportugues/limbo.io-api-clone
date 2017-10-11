package io.bandit.limbo.limbo.application.api.resources.companytraits.routing;

import org.springframework.beans.factory.annotation.Value;
import javax.inject.Named;

@Named("CompanyTraitsHttpRouter")
public class CompanyTraitsHttpRouter {

    private static final String RESOURCE_NAME = "company-traits";
    private static final String RESOURCE_ID = "{id}";

    public static final String COMPANYTRAITS_MANY_ROUTE = "/api/" + RESOURCE_NAME;
    public static final String COMPANYTRAITS_ONE_ROUTE = "/api/" + RESOURCE_NAME + "/" + RESOURCE_ID;
    public static final String COMPANYTRAITS_TALENT_ROUTE = COMPANYTRAITS_ONE_ROUTE + "/talents";

    public String getCompanyTraitsRoute() {
        return COMPANYTRAITS_MANY_ROUTE;
    }

    public String getCompanyTraitsRoute(final String id) {
        return COMPANYTRAITS_ONE_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getCompanyTraitsTalentRoute(final String id) {
        return COMPANYTRAITS_TALENT_ROUTE.replace(RESOURCE_ID, id);
    }
}
