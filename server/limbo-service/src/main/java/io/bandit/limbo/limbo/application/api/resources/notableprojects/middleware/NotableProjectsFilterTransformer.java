package io.bandit.limbo.limbo.application.api.resources.notableprojects.middleware;

import io.bandit.limbo.limbo.application.api.resources.notableprojects.marshallers.NotableProjectsResponse;
import io.bandit.limbo.limbo.modules.shared.middleware.FilterTransformer;
import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;

import javax.inject.Inject;
import javax.inject.Named;

@Named("NotableProjectsFilterTransformer")
public class NotableProjectsFilterTransformer extends FilterTransformer {

    @Inject
    public NotableProjectsFilterTransformer(TypeConverter typeConverter) {
        super(typeConverter, new NotableProjectsResponse());
    }
}
