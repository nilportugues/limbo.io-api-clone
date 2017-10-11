package io.bandit.limbo.limbo.application.api.resources.worktype.middleware;

import io.bandit.limbo.limbo.application.api.resources.worktype.marshallers.WorkTypeResponse;
import io.bandit.limbo.limbo.modules.shared.middleware.FilterTransformer;
import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;

import javax.inject.Inject;
import javax.inject.Named;

@Named("WorkTypeFilterTransformer")
public class WorkTypeFilterTransformer extends FilterTransformer {

    @Inject
    public WorkTypeFilterTransformer(TypeConverter typeConverter) {
        super(typeConverter, new WorkTypeResponse());
    }
}
