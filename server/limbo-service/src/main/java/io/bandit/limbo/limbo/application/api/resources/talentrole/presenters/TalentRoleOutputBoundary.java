package io.bandit.limbo.limbo.application.api.resources.talentrole.presenters;

import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;

public interface TalentRoleOutputBoundary {
    String success(final TalentRole talentRole);
    String notFound(final String talentRoleId);
    String paginatedResponse(final String queryParams, final Paginated<TalentRole> page);
    String outOfBoundResponse(int number, int size);
    String unauthorized();
    String forbidden();
    String internalServerError();
}
