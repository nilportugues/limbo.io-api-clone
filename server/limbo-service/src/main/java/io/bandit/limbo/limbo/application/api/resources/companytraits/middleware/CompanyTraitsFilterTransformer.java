package io.bandit.limbo.limbo.application.api.resources.companytraits.middleware;

import io.bandit.limbo.limbo.application.api.resources.companytraits.marshallers.CompanyTraitsResponse;
import io.bandit.limbo.limbo.modules.shared.middleware.FilterTransformer;
import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;

import javax.inject.Inject;
import javax.inject.Named;

@Named("CompanyTraitsFilterTransformer")
public class CompanyTraitsFilterTransformer extends FilterTransformer {

    @Inject
    public CompanyTraitsFilterTransformer(TypeConverter typeConverter) {
        super(typeConverter, new CompanyTraitsResponse());
    }
}
