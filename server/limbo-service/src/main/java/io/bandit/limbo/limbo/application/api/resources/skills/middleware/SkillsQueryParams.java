package io.bandit.limbo.limbo.application.api.resources.skills.middleware;

import io.bandit.limbo.limbo.modules.shared.middleware.QueryParams;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("SkillsQueryParams")
public class SkillsQueryParams extends QueryParams {

    @Inject
    public SkillsQueryParams(HttpServletRequest context, SkillsFilterTransformer transformer) {
        super(context, transformer);
    }
}
