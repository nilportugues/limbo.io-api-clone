package io.bandit.limbo.limbo.application.api.resources.talent.middleware;

import io.bandit.limbo.limbo.application.api.resources.talent.marshallers.TalentResponse;
import io.bandit.limbo.limbo.modules.shared.middleware.FilterTransformer;
import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;

import javax.inject.Inject;
import javax.inject.Named;

@Named("TalentFilterTransformer")
public class TalentFilterTransformer extends FilterTransformer {

    @Inject
    public TalentFilterTransformer(TypeConverter typeConverter) {
        super(typeConverter, new TalentResponse());
    }
}
