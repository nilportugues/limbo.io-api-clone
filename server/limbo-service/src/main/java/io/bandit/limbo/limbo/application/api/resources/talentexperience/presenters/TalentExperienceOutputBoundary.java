package io.bandit.limbo.limbo.application.api.resources.talentexperience.presenters;

import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;

public interface TalentExperienceOutputBoundary {
    String success(final TalentExperience talentExperience);
    String notFound(final String talentExperienceId);
    String paginatedResponse(final String queryParams, final Paginated<TalentExperience> page);
    String outOfBoundResponse(int number, int size);
    String unauthorized();
    String forbidden();
    String internalServerError();
}
