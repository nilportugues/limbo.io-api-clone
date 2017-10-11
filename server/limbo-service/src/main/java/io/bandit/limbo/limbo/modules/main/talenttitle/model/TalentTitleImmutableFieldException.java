package io.bandit.limbo.limbo.modules.main.talenttitle.model;

public class TalentTitleImmutableFieldException extends Exception {

    public TalentTitleImmutableFieldException(String field) {
        super("TalentTitle field '"+field+"' cannot be changed.");
    }
}
