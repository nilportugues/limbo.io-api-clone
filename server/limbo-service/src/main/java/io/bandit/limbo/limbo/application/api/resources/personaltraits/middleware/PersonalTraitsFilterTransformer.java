package io.bandit.limbo.limbo.application.api.resources.personaltraits.middleware;

import io.bandit.limbo.limbo.application.api.resources.personaltraits.marshallers.PersonalTraitsResponse;
import io.bandit.limbo.limbo.modules.shared.middleware.FilterTransformer;
import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;

import javax.inject.Inject;
import javax.inject.Named;

@Named("PersonalTraitsFilterTransformer")
public class PersonalTraitsFilterTransformer extends FilterTransformer {

    @Inject
    public PersonalTraitsFilterTransformer(TypeConverter typeConverter) {
        super(typeConverter, new PersonalTraitsResponse());
    }
}
