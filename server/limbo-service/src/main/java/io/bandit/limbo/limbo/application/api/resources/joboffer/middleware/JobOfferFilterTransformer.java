package io.bandit.limbo.limbo.application.api.resources.joboffer.middleware;

import io.bandit.limbo.limbo.application.api.resources.joboffer.marshallers.JobOfferResponse;
import io.bandit.limbo.limbo.modules.shared.middleware.FilterTransformer;
import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;

import javax.inject.Inject;
import javax.inject.Named;

@Named("JobOfferFilterTransformer")
public class JobOfferFilterTransformer extends FilterTransformer {

    @Inject
    public JobOfferFilterTransformer(TypeConverter typeConverter) {
        super(typeConverter, new JobOfferResponse());
    }
}
