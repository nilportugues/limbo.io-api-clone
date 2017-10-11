package io.bandit.limbo.limbo.application.api.resources.talentprofile.presenters;

import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;

public interface TalentProfileOutputBoundary {
    String success(final TalentProfile talentProfile);
    String notFound(final String talentProfileId);
    String paginatedResponse(final String queryParams, final Paginated<TalentProfile> page);
    String outOfBoundResponse(int number, int size);
    String unauthorized();
    String forbidden();
    String internalServerError();
}
