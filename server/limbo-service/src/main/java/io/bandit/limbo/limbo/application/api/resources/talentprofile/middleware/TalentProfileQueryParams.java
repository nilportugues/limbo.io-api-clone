package io.bandit.limbo.limbo.application.api.resources.talentprofile.middleware;

import io.bandit.limbo.limbo.modules.shared.middleware.QueryParams;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("TalentProfileQueryParams")
public class TalentProfileQueryParams extends QueryParams {

    @Inject
    public TalentProfileQueryParams(HttpServletRequest context, TalentProfileFilterTransformer transformer) {
        super(context, transformer);
    }
}
