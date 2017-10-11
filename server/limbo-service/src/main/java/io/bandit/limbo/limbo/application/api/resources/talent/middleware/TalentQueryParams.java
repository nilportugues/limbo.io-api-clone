package io.bandit.limbo.limbo.application.api.resources.talent.middleware;

import io.bandit.limbo.limbo.modules.shared.middleware.QueryParams;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("TalentQueryParams")
public class TalentQueryParams extends QueryParams {

    @Inject
    public TalentQueryParams(HttpServletRequest context, TalentFilterTransformer transformer) {
        super(context, transformer);
    }
}
