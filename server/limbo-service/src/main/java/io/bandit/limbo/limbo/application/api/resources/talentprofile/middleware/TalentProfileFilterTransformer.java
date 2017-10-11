package io.bandit.limbo.limbo.application.api.resources.talentprofile.middleware;

import io.bandit.limbo.limbo.application.api.resources.talentprofile.marshallers.TalentProfileResponse;
import io.bandit.limbo.limbo.modules.shared.middleware.FilterTransformer;
import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;

import javax.inject.Inject;
import javax.inject.Named;

@Named("TalentProfileFilterTransformer")
public class TalentProfileFilterTransformer extends FilterTransformer {

    @Inject
    public TalentProfileFilterTransformer(TypeConverter typeConverter) {
        super(typeConverter, new TalentProfileResponse());
    }
}
