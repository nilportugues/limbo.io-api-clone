package io.bandit.limbo.limbo.application.api.resources.talenttitle.presenters;

import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;

public interface TalentTitleOutputBoundary {
    String success(final TalentTitle talentTitle);
    String notFound(final String talentTitleId);
    String paginatedResponse(final String queryParams, final Paginated<TalentTitle> page);
    String outOfBoundResponse(int number, int size);
    String unauthorized();
    String forbidden();
    String internalServerError();
}
