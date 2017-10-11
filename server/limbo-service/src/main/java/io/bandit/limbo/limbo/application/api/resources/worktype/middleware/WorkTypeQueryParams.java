package io.bandit.limbo.limbo.application.api.resources.worktype.middleware;

import io.bandit.limbo.limbo.modules.shared.middleware.QueryParams;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("WorkTypeQueryParams")
public class WorkTypeQueryParams extends QueryParams {

    @Inject
    public WorkTypeQueryParams(HttpServletRequest context, WorkTypeFilterTransformer transformer) {
        super(context, transformer);
    }
}
