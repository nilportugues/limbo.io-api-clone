package io.bandit.limbo.limbo.modules.main.skills.model;

public class SkillsImmutableFieldException extends Exception {

    public SkillsImmutableFieldException(String field) {
        super("Skills field '"+field+"' cannot be changed.");
    }
}
