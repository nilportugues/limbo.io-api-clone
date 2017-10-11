package io.bandit.limbo.limbo.application.api.resources.personaltraits.presenters;

import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;

public interface PersonalTraitsOutputBoundary {
    String success(final PersonalTraits personalTraits);
    String notFound(final String personalTraitsId);
    String paginatedResponse(final String queryParams, final Paginated<PersonalTraits> page);
    String outOfBoundResponse(int number, int size);
    String unauthorized();
    String forbidden();
    String internalServerError();
}
