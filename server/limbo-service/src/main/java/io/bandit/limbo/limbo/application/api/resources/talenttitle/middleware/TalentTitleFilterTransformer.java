package io.bandit.limbo.limbo.application.api.resources.talenttitle.middleware;

import io.bandit.limbo.limbo.application.api.resources.talenttitle.marshallers.TalentTitleResponse;
import io.bandit.limbo.limbo.modules.shared.middleware.FilterTransformer;
import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;

import javax.inject.Inject;
import javax.inject.Named;

@Named("TalentTitleFilterTransformer")
public class TalentTitleFilterTransformer extends FilterTransformer {

    @Inject
    public TalentTitleFilterTransformer(TypeConverter typeConverter) {
        super(typeConverter, new TalentTitleResponse());
    }
}
