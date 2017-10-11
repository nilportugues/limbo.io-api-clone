package io.bandit.limbo.limbo.modules.main.talentprofile.model;

public class TalentProfileImmutableFieldException extends Exception {

    public TalentProfileImmutableFieldException(String field) {
        super("TalentProfile field '"+field+"' cannot be changed.");
    }
}
