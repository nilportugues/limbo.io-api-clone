package io.bandit.limbo.limbo.modules.main.socialnetworks.model;

public class SocialNetworksImmutableFieldException extends Exception {

    public SocialNetworksImmutableFieldException(String field) {
        super("SocialNetworks field '"+field+"' cannot be changed.");
    }
}
