package io.bandit.limbo.limbo.application.api.resources.worktype.presenters;

import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;

public interface WorkTypeOutputBoundary {
    String success(final WorkType workType);
    String notFound(final String workTypeId);
    String paginatedResponse(final String queryParams, final Paginated<WorkType> page);
    String outOfBoundResponse(int number, int size);
    String unauthorized();
    String forbidden();
    String internalServerError();
}
