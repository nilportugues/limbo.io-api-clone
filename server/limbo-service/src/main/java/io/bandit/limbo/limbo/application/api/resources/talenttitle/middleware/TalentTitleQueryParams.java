package io.bandit.limbo.limbo.application.api.resources.talenttitle.middleware;

import io.bandit.limbo.limbo.modules.shared.middleware.QueryParams;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("TalentTitleQueryParams")
public class TalentTitleQueryParams extends QueryParams {

    @Inject
    public TalentTitleQueryParams(HttpServletRequest context, TalentTitleFilterTransformer transformer) {
        super(context, transformer);
    }
}
