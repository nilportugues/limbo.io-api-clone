package io.bandit.limbo.limbo.application.api.resources.talentexperience.middleware;

import io.bandit.limbo.limbo.modules.shared.middleware.QueryParams;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("TalentExperienceQueryParams")
public class TalentExperienceQueryParams extends QueryParams {

    @Inject
    public TalentExperienceQueryParams(HttpServletRequest context, TalentExperienceFilterTransformer transformer) {
        super(context, transformer);
    }
}
