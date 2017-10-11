package io.bandit.limbo.limbo.application.api.resources.city.middleware;

import io.bandit.limbo.limbo.modules.shared.middleware.QueryParams;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("CityQueryParams")
public class CityQueryParams extends QueryParams {

    @Inject
    public CityQueryParams(HttpServletRequest context, CityFilterTransformer transformer) {
        super(context, transformer);
    }
}
