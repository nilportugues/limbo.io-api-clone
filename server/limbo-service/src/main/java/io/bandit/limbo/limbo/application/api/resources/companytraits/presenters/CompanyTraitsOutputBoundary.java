package io.bandit.limbo.limbo.application.api.resources.companytraits.presenters;

import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;

public interface CompanyTraitsOutputBoundary {
    String success(final CompanyTraits companyTraits);
    String notFound(final String companyTraitsId);
    String paginatedResponse(final String queryParams, final Paginated<CompanyTraits> page);
    String outOfBoundResponse(int number, int size);
    String unauthorized();
    String forbidden();
    String internalServerError();
}
