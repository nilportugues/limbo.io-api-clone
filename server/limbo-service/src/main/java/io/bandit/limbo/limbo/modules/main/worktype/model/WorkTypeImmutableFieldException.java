package io.bandit.limbo.limbo.modules.main.worktype.model;

public class WorkTypeImmutableFieldException extends Exception {

    public WorkTypeImmutableFieldException(String field) {
        super("WorkType field '"+field+"' cannot be changed.");
    }
}
