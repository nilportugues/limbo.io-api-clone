package io.bandit.limbo.limbo.application.api.resources.city.presenters;

import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;

public interface CityOutputBoundary {
    String success(final City city);
    String notFound(final String cityId);
    String paginatedResponse(final String queryParams, final Paginated<City> page);
    String outOfBoundResponse(int number, int size);
    String unauthorized();
    String forbidden();
    String internalServerError();
}
