package io.bandit.limbo.limbo.application.api.resources.country.middleware;

import io.bandit.limbo.limbo.application.api.resources.country.marshallers.CountryResponse;
import io.bandit.limbo.limbo.modules.shared.middleware.FilterTransformer;
import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;

import javax.inject.Inject;
import javax.inject.Named;

@Named("CountryFilterTransformer")
public class CountryFilterTransformer extends FilterTransformer {

    @Inject
    public CountryFilterTransformer(TypeConverter typeConverter) {
        super(typeConverter, new CountryResponse());
    }
}
