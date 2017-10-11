package io.bandit.limbo.limbo.application.api.resources.skills.routing;

import org.springframework.beans.factory.annotation.Value;
import javax.inject.Named;

@Named("SkillsHttpRouter")
public class SkillsHttpRouter {

    private static final String RESOURCE_NAME = "skills";
    private static final String RESOURCE_ID = "{id}";

    public static final String SKILLS_MANY_ROUTE = "/api/" + RESOURCE_NAME;
    public static final String SKILLS_ONE_ROUTE = "/api/" + RESOURCE_NAME + "/" + RESOURCE_ID;
    public static final String SKILLS_TALENT_ROUTE = SKILLS_ONE_ROUTE + "/talents";

    public String getSkillsRoute() {
        return SKILLS_MANY_ROUTE;
    }

    public String getSkillsRoute(final String id) {
        return SKILLS_ONE_ROUTE.replace(RESOURCE_ID, id);
    }

    public String getSkillsTalentRoute(final String id) {
        return SKILLS_TALENT_ROUTE.replace(RESOURCE_ID, id);
    }
}
