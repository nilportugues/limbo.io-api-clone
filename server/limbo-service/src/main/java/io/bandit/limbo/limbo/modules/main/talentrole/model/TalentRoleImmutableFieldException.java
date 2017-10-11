package io.bandit.limbo.limbo.modules.main.talentrole.model;

public class TalentRoleImmutableFieldException extends Exception {

    public TalentRoleImmutableFieldException(String field) {
        super("TalentRole field '"+field+"' cannot be changed.");
    }
}
