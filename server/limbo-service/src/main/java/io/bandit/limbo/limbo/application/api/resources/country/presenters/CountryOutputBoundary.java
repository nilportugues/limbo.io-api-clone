package io.bandit.limbo.limbo.application.api.resources.country.presenters;

import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;

public interface CountryOutputBoundary {
    String success(final Country country);
    String notFound(final String countryId);
    String paginatedResponse(final String queryParams, final Paginated<Country> page);
    String outOfBoundResponse(int number, int size);
    String unauthorized();
    String forbidden();
    String internalServerError();
}
