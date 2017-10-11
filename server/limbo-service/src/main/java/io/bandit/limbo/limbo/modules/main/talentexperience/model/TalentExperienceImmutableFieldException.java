package io.bandit.limbo.limbo.modules.main.talentexperience.model;

public class TalentExperienceImmutableFieldException extends Exception {

    public TalentExperienceImmutableFieldException(String field) {
        super("TalentExperience field '"+field+"' cannot be changed.");
    }
}
