package io.bandit.limbo.limbo.modules.main.country.model;

public class CountryImmutableFieldException extends Exception {

    public CountryImmutableFieldException(String field) {
        super("Country field '"+field+"' cannot be changed.");
    }
}
