package io.bandit.limbo.limbo.application.api.resources.city.routing;

import org.springframework.beans.factory.annotation.Value;
import javax.inject.Named;

@Named("CityHttpRouter")
public class CityHttpRouter {

    private static final String RESOURCE_NAME = "cities";
    private static final String RESOURCE_ID = "{id}";

    public static final String CITY_MANY_ROUTE = "/api/" + RESOURCE_NAME;
    public static final String CITY_ONE_ROUTE = "/api/" + RESOURCE_NAME + "/" + RESOURCE_ID;
    public static final String CITY_COUNTRY_ROUTE = CITY_ONE_ROUTE + "/countries";

    public String getCitiesRoute() {
        return CITY_MANY_ROUTE;
    }

    public String getCityRoute(final String id) {
        return CITY_ONE_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getCityCountryRoute(final String id) {
        return CITY_COUNTRY_ROUTE.replace(RESOURCE_ID, id);
    }
}
