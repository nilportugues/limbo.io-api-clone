package io.bandit.limbo.limbo.application.api.resources.talentrole.middleware;

import io.bandit.limbo.limbo.application.api.resources.talentrole.marshallers.TalentRoleResponse;
import io.bandit.limbo.limbo.modules.shared.middleware.FilterTransformer;
import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;

import javax.inject.Inject;
import javax.inject.Named;

@Named("TalentRoleFilterTransformer")
public class TalentRoleFilterTransformer extends FilterTransformer {

    @Inject
    public TalentRoleFilterTransformer(TypeConverter typeConverter) {
        super(typeConverter, new TalentRoleResponse());
    }
}
