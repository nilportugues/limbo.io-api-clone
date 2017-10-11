package io.bandit.limbo.limbo.application.api.resources.socialnetworks.middleware;

import io.bandit.limbo.limbo.modules.shared.middleware.QueryParams;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("SocialNetworksQueryParams")
public class SocialNetworksQueryParams extends QueryParams {

    @Inject
    public SocialNetworksQueryParams(HttpServletRequest context, SocialNetworksFilterTransformer transformer) {
        super(context, transformer);
    }
}
