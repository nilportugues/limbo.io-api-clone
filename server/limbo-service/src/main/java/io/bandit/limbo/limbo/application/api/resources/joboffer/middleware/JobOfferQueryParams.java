package io.bandit.limbo.limbo.application.api.resources.joboffer.middleware;

import io.bandit.limbo.limbo.modules.shared.middleware.QueryParams;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("JobOfferQueryParams")
public class JobOfferQueryParams extends QueryParams {

    @Inject
    public JobOfferQueryParams(HttpServletRequest context, JobOfferFilterTransformer transformer) {
        super(context, transformer);
    }
}
