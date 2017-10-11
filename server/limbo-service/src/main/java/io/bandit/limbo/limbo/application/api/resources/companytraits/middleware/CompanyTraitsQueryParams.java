package io.bandit.limbo.limbo.application.api.resources.companytraits.middleware;

import io.bandit.limbo.limbo.modules.shared.middleware.QueryParams;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("CompanyTraitsQueryParams")
public class CompanyTraitsQueryParams extends QueryParams {

    @Inject
    public CompanyTraitsQueryParams(HttpServletRequest context, CompanyTraitsFilterTransformer transformer) {
        super(context, transformer);
    }
}
