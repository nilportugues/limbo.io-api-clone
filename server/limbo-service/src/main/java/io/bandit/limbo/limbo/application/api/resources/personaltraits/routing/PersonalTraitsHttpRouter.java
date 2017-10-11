package io.bandit.limbo.limbo.application.api.resources.personaltraits.routing;

import org.springframework.beans.factory.annotation.Value;
import javax.inject.Named;

@Named("PersonalTraitsHttpRouter")
public class PersonalTraitsHttpRouter {

    private static final String RESOURCE_NAME = "personal-traits";
    private static final String RESOURCE_ID = "{id}";

    public static final String PERSONALTRAITS_MANY_ROUTE = "/api/" + RESOURCE_NAME;
    public static final String PERSONALTRAITS_ONE_ROUTE = "/api/" + RESOURCE_NAME + "/" + RESOURCE_ID;
    public static final String PERSONALTRAITS_TALENT_ROUTE = PERSONALTRAITS_ONE_ROUTE + "/talents";

    public String getPersonalTraitsRoute() {
        return PERSONALTRAITS_MANY_ROUTE;
    }

    public String getPersonalTraitsRoute(final String id) {
        return PERSONALTRAITS_ONE_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getPersonalTraitsTalentRoute(final String id) {
        return PERSONALTRAITS_TALENT_ROUTE.replace(RESOURCE_ID, id);
    }
}
