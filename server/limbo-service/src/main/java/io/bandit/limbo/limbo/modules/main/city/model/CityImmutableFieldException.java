package io.bandit.limbo.limbo.modules.main.city.model;

public class CityImmutableFieldException extends Exception {

    public CityImmutableFieldException(String field) {
        super("City field '"+field+"' cannot be changed.");
    }
}
