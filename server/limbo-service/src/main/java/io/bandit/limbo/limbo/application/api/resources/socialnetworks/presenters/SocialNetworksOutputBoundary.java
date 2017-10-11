package io.bandit.limbo.limbo.application.api.resources.socialnetworks.presenters;

import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;

public interface SocialNetworksOutputBoundary {
    String success(final SocialNetworks socialNetworks);
    String notFound(final String socialNetworksId);
    String paginatedResponse(final String queryParams, final Paginated<SocialNetworks> page);
    String outOfBoundResponse(int number, int size);
    String unauthorized();
    String forbidden();
    String internalServerError();
}
