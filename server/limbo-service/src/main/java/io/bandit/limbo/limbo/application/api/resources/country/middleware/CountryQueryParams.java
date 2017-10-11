package io.bandit.limbo.limbo.application.api.resources.country.middleware;

import io.bandit.limbo.limbo.modules.shared.middleware.QueryParams;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("CountryQueryParams")
public class CountryQueryParams extends QueryParams {

    @Inject
    public CountryQueryParams(HttpServletRequest context, CountryFilterTransformer transformer) {
        super(context, transformer);
    }
}
