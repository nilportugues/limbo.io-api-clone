package io.bandit.limbo.limbo.application.api.resources.notableprojects.presenters;

import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;

public interface NotableProjectsOutputBoundary {
    String success(final NotableProjects notableProjects);
    String notFound(final String notableProjectsId);
    String paginatedResponse(final String queryParams, final Paginated<NotableProjects> page);
    String outOfBoundResponse(int number, int size);
    String unauthorized();
    String forbidden();
    String internalServerError();
}
