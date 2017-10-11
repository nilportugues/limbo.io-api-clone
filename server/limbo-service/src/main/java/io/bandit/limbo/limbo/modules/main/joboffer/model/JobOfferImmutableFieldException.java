package io.bandit.limbo.limbo.modules.main.joboffer.model;

public class JobOfferImmutableFieldException extends Exception {

    public JobOfferImmutableFieldException(String field) {
        super("JobOffer field '"+field+"' cannot be changed.");
    }
}
