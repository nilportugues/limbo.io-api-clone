package io.bandit.limbo.limbo.modules.main.notableprojects.model;

public class NotableProjectsImmutableFieldException extends Exception {

    public NotableProjectsImmutableFieldException(String field) {
        super("NotableProjects field '"+field+"' cannot be changed.");
    }
}
