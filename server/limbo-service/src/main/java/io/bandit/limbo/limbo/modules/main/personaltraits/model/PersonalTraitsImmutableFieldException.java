package io.bandit.limbo.limbo.modules.main.personaltraits.model;

public class PersonalTraitsImmutableFieldException extends Exception {

    public PersonalTraitsImmutableFieldException(String field) {
        super("PersonalTraits field '"+field+"' cannot be changed.");
    }
}
