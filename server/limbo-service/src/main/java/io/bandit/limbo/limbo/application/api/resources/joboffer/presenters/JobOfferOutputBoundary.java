package io.bandit.limbo.limbo.application.api.resources.joboffer.presenters;

import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;

public interface JobOfferOutputBoundary {
    String success(final JobOffer jobOffer);
    String notFound(final String jobOfferId);
    String paginatedResponse(final String queryParams, final Paginated<JobOffer> page);
    String outOfBoundResponse(int number, int size);
    String unauthorized();
    String forbidden();
    String internalServerError();
}
