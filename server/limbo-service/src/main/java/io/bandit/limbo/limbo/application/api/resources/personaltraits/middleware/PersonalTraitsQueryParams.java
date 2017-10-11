package io.bandit.limbo.limbo.application.api.resources.personaltraits.middleware;

import io.bandit.limbo.limbo.modules.shared.middleware.QueryParams;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("PersonalTraitsQueryParams")
public class PersonalTraitsQueryParams extends QueryParams {

    @Inject
    public PersonalTraitsQueryParams(HttpServletRequest context, PersonalTraitsFilterTransformer transformer) {
        super(context, transformer);
    }
}
