package io.bandit.limbo.limbo.application.api.resources.country.metrics;

import io.bandit.limbo.limbo.modules.shared.metrics.Metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

@Named
public class CountryApiMetrics extends Metrics {

    private static final String COUNTRY = ".country";
    private static final String COUNTRY_CITY = ".country_city";

    private static Counter countryErrorCounter;
    private static Counter countrySuccessCounter;
    private static Counter countryNotFoundCounter;
    private static Counter countryCreatedCounter;
    private static Counter countryUpdatedCounter;
    private static Counter countryDeletedCounter;

    private static Counter countryCityErrorCounter;
    private static Counter countryCitySuccessCounter;
    private static Counter countryCityNotFoundCounter;
    private static Counter countryCityCreatedCounter;
    private static Counter countryCityUpdatedCounter;
    private static Counter countryCityDeletedCounter;

    public CountryApiMetrics(final MeterRegistry registry) {
        countryErrorCounter = registry.counter(API_ERROR + COUNTRY);
        countrySuccessCounter = registry.counter(API_FETCH + COUNTRY);
        countryNotFoundCounter = registry.counter(API_NOTFOUND + COUNTRY);
        countryCreatedCounter = registry.counter(API_CREATED + COUNTRY);
        countryUpdatedCounter = registry.counter(API_UPDATED + COUNTRY);
        countryDeletedCounter = registry.counter(API_DELETED + COUNTRY);

        countryCityErrorCounter= registry.counter(API_ERROR + COUNTRY_CITY);
        countryCitySuccessCounter = registry.counter(API_FETCH + COUNTRY_CITY);
        countryCityCreatedCounter = registry.counter(API_CREATED + COUNTRY_CITY);
        countryCityUpdatedCounter = registry.counter(API_UPDATED + COUNTRY_CITY);
        countryCityDeletedCounter = registry.counter(API_DELETED + COUNTRY_CITY);
        countryCityNotFoundCounter = registry.counter(API_NOTFOUND + COUNTRY_CITY);

    }

    public void incrementCountryError() {
        increment(countryErrorCounter);
    }

    public void incrementCountrySuccess() {
        increment(countrySuccessCounter);
    }

    public void incrementCountryNotFound() {
        increment(countryNotFoundCounter);
    }

    public void incrementCountryCreated() {
        increment(countryCreatedCounter);
    }

    public void incrementCountryUpdated() {
        increment(countryUpdatedCounter);
    }

    public void incrementCountryDeleted() {
        increment(countryDeletedCounter);
    }

    public void incrementCountryCityError() {
        increment(countryCityErrorCounter);
    }

    public void incrementCountryCitySuccess() {
        increment(countryCitySuccessCounter);
    }

    public void incrementCountryCityNotFound() {
        increment(countryCityNotFoundCounter);
    }

    public void incrementCountryCityCreated() {
        increment(countryCityCreatedCounter);
    }

    public void incrementCountryCityUpdated() {
        increment(countryCityUpdatedCounter);
    }

    public void incrementCountryCityDeleted() {
        increment(countryCityDeletedCounter);
    }


    private void increment(final Counter counter) {
        CompletableFuture.runAsync(counter::increment);
    }
}
