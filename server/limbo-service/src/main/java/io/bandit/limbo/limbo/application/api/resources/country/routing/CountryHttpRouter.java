package io.bandit.limbo.limbo.application.api.resources.country.routing;

import org.springframework.beans.factory.annotation.Value;
import javax.inject.Named;

@Named("CountryHttpRouter")
public class CountryHttpRouter {

    private static final String RESOURCE_NAME = "countries";
    private static final String RESOURCE_ID = "{id}";

    public static final String COUNTRY_MANY_ROUTE = "/api/" + RESOURCE_NAME;
    public static final String COUNTRY_ONE_ROUTE = "/api/" + RESOURCE_NAME + "/" + RESOURCE_ID;
    public static final String COUNTRY_CITY_ROUTE = COUNTRY_ONE_ROUTE + "/cities";
    public static final String COUNTRY_CITY_DELETE_BATCH_ROUTE = COUNTRY_ONE_ROUTE + "/cities/batch";
    public static final String COUNTRY_CITY_DELETE_ALL_ROUTE = COUNTRY_ONE_ROUTE + "/cities/all";

    public String getCountriesRoute() {
        return COUNTRY_MANY_ROUTE;
    }

    public String getCountryRoute(final String id) {
        return COUNTRY_ONE_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getCountryCityRoute(final String id) {
        return COUNTRY_CITY_ROUTE.replace(RESOURCE_ID, id);
    }
}
