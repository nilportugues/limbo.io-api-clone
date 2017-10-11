package io.bandit.limbo.limbo.modules.main.companytraits.model;

public class CompanyTraitsImmutableFieldException extends Exception {

    public CompanyTraitsImmutableFieldException(String field) {
        super("CompanyTraits field '"+field+"' cannot be changed.");
    }
}
