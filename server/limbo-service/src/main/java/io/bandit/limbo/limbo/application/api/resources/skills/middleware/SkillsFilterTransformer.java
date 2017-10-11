package io.bandit.limbo.limbo.application.api.resources.skills.middleware;

import io.bandit.limbo.limbo.application.api.resources.skills.marshallers.SkillsResponse;
import io.bandit.limbo.limbo.modules.shared.middleware.FilterTransformer;
import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;

import javax.inject.Inject;
import javax.inject.Named;

@Named("SkillsFilterTransformer")
public class SkillsFilterTransformer extends FilterTransformer {

    @Inject
    public SkillsFilterTransformer(TypeConverter typeConverter) {
        super(typeConverter, new SkillsResponse());
    }
}
