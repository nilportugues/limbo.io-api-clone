package io.bandit.limbo.limbo.application.api.resources.city.middleware;

import io.bandit.limbo.limbo.application.api.resources.city.marshallers.CityResponse;
import io.bandit.limbo.limbo.modules.shared.middleware.FilterTransformer;
import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;

import javax.inject.Inject;
import javax.inject.Named;

@Named("CityFilterTransformer")
public class CityFilterTransformer extends FilterTransformer {

    @Inject
    public CityFilterTransformer(TypeConverter typeConverter) {
        super(typeConverter, new CityResponse());
    }
}
