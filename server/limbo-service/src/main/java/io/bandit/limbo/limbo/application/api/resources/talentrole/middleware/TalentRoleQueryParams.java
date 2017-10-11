package io.bandit.limbo.limbo.application.api.resources.talentrole.middleware;

import io.bandit.limbo.limbo.modules.shared.middleware.QueryParams;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("TalentRoleQueryParams")
public class TalentRoleQueryParams extends QueryParams {

    @Inject
    public TalentRoleQueryParams(HttpServletRequest context, TalentRoleFilterTransformer transformer) {
        super(context, transformer);
    }
}
