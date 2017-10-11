package io.bandit.limbo.limbo.application.api.resources.skills.presenters;

import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;

public interface SkillsOutputBoundary {
    String success(final Skills skills);
    String notFound(final String skillsId);
    String paginatedResponse(final String queryParams, final Paginated<Skills> page);
    String outOfBoundResponse(int number, int size);
    String unauthorized();
    String forbidden();
    String internalServerError();
}
