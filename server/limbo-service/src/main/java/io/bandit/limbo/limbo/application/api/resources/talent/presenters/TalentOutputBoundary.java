package io.bandit.limbo.limbo.application.api.resources.talent.presenters;

import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;

public interface TalentOutputBoundary {
    String success(final Talent talent);
    String notFound(final String talentId);
    String paginatedResponse(final String queryParams, final Paginated<Talent> page);
    String outOfBoundResponse(int number, int size);
    String unauthorized();
    String forbidden();
    String internalServerError();
}
