package io.bandit.limbo.limbo.application.api.resources.talentexperience.middleware;

import io.bandit.limbo.limbo.application.api.resources.talentexperience.marshallers.TalentExperienceResponse;
import io.bandit.limbo.limbo.modules.shared.middleware.FilterTransformer;
import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;

import javax.inject.Inject;
import javax.inject.Named;

@Named("TalentExperienceFilterTransformer")
public class TalentExperienceFilterTransformer extends FilterTransformer {

    @Inject
    public TalentExperienceFilterTransformer(TypeConverter typeConverter) {
        super(typeConverter, new TalentExperienceResponse());
    }
}
