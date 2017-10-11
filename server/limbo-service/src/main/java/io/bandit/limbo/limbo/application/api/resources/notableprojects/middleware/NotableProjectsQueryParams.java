package io.bandit.limbo.limbo.application.api.resources.notableprojects.middleware;

import io.bandit.limbo.limbo.modules.shared.middleware.QueryParams;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("NotableProjectsQueryParams")
public class NotableProjectsQueryParams extends QueryParams {

    @Inject
    public NotableProjectsQueryParams(HttpServletRequest context, NotableProjectsFilterTransformer transformer) {
        super(context, transformer);
    }
}
