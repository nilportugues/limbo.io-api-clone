package io.bandit.limbo.limbo.application.api.resources.city.metrics;

import io.bandit.limbo.limbo.modules.shared.metrics.Metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

@Named
public class CityApiMetrics extends Metrics {

    private static final String CITY = ".city";
    private static final String CITY_COUNTRY = ".city_country";

    private static Counter cityErrorCounter;
    private static Counter citySuccessCounter;
    private static Counter cityNotFoundCounter;
    private static Counter cityCreatedCounter;
    private static Counter cityUpdatedCounter;
    private static Counter cityDeletedCounter;

    private static Counter cityCountryErrorCounter;
    private static Counter cityCountrySuccessCounter;
    private static Counter cityCountryNotFoundCounter;
    private static Counter cityCountryCreatedCounter;
    private static Counter cityCountryUpdatedCounter;
    private static Counter cityCountryDeletedCounter;

    public CityApiMetrics(final MeterRegistry registry) {
        cityErrorCounter = registry.counter(API_ERROR + CITY);
        citySuccessCounter = registry.counter(API_FETCH + CITY);
        cityNotFoundCounter = registry.counter(API_NOTFOUND + CITY);
        cityCreatedCounter = registry.counter(API_CREATED + CITY);
        cityUpdatedCounter = registry.counter(API_UPDATED + CITY);
        cityDeletedCounter = registry.counter(API_DELETED + CITY);

        cityCountryErrorCounter= registry.counter(API_ERROR + CITY_COUNTRY);
        cityCountrySuccessCounter = registry.counter(API_FETCH + CITY_COUNTRY);
        cityCountryCreatedCounter = registry.counter(API_CREATED + CITY_COUNTRY);
        cityCountryUpdatedCounter = registry.counter(API_UPDATED + CITY_COUNTRY);
        cityCountryDeletedCounter = registry.counter(API_DELETED + CITY_COUNTRY);
        cityCountryNotFoundCounter = registry.counter(API_NOTFOUND + CITY_COUNTRY);

    }

    public void incrementCityError() {
        increment(cityErrorCounter);
    }

    public void incrementCitySuccess() {
        increment(citySuccessCounter);
    }

    public void incrementCityNotFound() {
        increment(cityNotFoundCounter);
    }

    public void incrementCityCreated() {
        increment(cityCreatedCounter);
    }

    public void incrementCityUpdated() {
        increment(cityUpdatedCounter);
    }

    public void incrementCityDeleted() {
        increment(cityDeletedCounter);
    }

    public void incrementCityCountryError() {
        increment(cityCountryErrorCounter);
    }

    public void incrementCityCountrySuccess() {
        increment(cityCountrySuccessCounter);
    }

    public void incrementCityCountryNotFound() {
        increment(cityCountryNotFoundCounter);
    }

    public void incrementCityCountryCreated() {
        increment(cityCountryCreatedCounter);
    }

    public void incrementCityCountryUpdated() {
        increment(cityCountryUpdatedCounter);
    }

    public void incrementCityCountryDeleted() {
        increment(cityCountryDeletedCounter);
    }


    private void increment(final Counter counter) {
        CompletableFuture.runAsync(counter::increment);
    }
}
