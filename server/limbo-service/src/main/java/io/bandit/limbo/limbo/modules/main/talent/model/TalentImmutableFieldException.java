package io.bandit.limbo.limbo.modules.main.talent.model;

public class TalentImmutableFieldException extends Exception {

    public TalentImmutableFieldException(String field) {
        super("Talent field '"+field+"' cannot be changed.");
    }
}
