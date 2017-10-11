package io.bandit.limbo.limbo.application.api.resources.socialnetworks.middleware;

import io.bandit.limbo.limbo.application.api.resources.socialnetworks.marshallers.SocialNetworksResponse;
import io.bandit.limbo.limbo.modules.shared.middleware.FilterTransformer;
import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;

import javax.inject.Inject;
import javax.inject.Named;

@Named("SocialNetworksFilterTransformer")
public class SocialNetworksFilterTransformer extends FilterTransformer {

    @Inject
    public SocialNetworksFilterTransformer(TypeConverter typeConverter) {
        super(typeConverter, new SocialNetworksResponse());
    }
}
