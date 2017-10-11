package io.bandit.limbo.limbo.application.api.resources.talentrole.presenters;

import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.application.api.resources.talentrole.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talentrole.routing.TalentRoleHttpRouter;
import io.bandit.limbo.limbo.application.api.presenters.vnderror.VndError;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import javax.inject.Named;

@Named("TalentRoleJsonPresenter")
public class TalentRoleJsonPresenter implements TalentRoleOutputBoundary {

    private static final String NOT_FOUND_MESSAGE = "TalentRole with id '%s' was not found.";
    private static final String UNAUTHORIZED_MESSAGE = "You are required to authenticate in order to access this resource.";
    private static final String FORBIDDEN_MESSAGE = "You are not authorized to access this resource.";
    private static final String INTERNAL_ERROR_MESSAGE = "We cannot resolve your request right now. Please try again later.";
    private static final String OUT_OF_BOUNDS_MESSAGE = "Could not return page number '%s' of size '%s' because no more results exist.";

    private final ObjectMapper objectMapper;
    private final TalentRoleHttpRouter router;

    public TalentRoleJsonPresenter(final ObjectMapper objectMapper, final TalentRoleHttpRouter router) {
        this.objectMapper = objectMapper;
        this.router = router;
    }

    public String success(final TalentRole talentRole) {
        try {
            final TalentRoleResponse response = TalentRoleResponse.fromTalentRole(
                    talentRole,
                    router.getTalentRolesRoute(),
                    router.getTalentRoleRoute(talentRole.getId()));

            return format(response);
        } catch (Throwable t) {
            return internalServerError();
        }
    }


    public String notFound(final String talentRoleId) {
        try {

            final String message = String.format(NOT_FOUND_MESSAGE, talentRoleId);
            final VndError.VndErrors vndErrors = new VndError.VndErrors(new ArrayList<>());
            vndErrors.addError(new VndError.Error(message, "id"));

            final VndError response = new VndError();
            response.setEmbedded(vndErrors);

            return format(response);
        } catch (Throwable t) {
            return internalServerError();
        }
    }

    public String paginatedResponse(final String queryParams, final Paginated<TalentRole> page) {
        try {
            final TalentRolePaginatedResponse response = new TalentRolePaginatedResponse(
            router.getTalentRolesRoute(),
            queryParams,
            page);

            return format(response);
        } catch (Throwable t) {
            return internalServerError();
        }
    }

    @Override
    public String outOfBoundResponse(int number, int size) {
        try {
            final String message = String.format(OUT_OF_BOUNDS_MESSAGE, number, size);
            final VndError.VndErrors vndErrors = new VndError.VndErrors(new ArrayList<>());
            vndErrors.addError(new VndError.Error(message));

            final VndError response = new VndError();
            response.setEmbedded(vndErrors);

            return format(response);
        } catch (Throwable t) {
            return internalServerError();
        }
    }

    public String unauthorized() {
        try {
            final VndError.VndErrors vndErrors = new VndError.VndErrors(new ArrayList<>());
            vndErrors.addError(new VndError.Error(UNAUTHORIZED_MESSAGE));

            final VndError response = new VndError();
            response.setEmbedded(vndErrors);

            return format(response);
        } catch (Throwable t) {
            return internalServerError();
        }
    }

    public String forbidden() {
        try {
            final VndError.VndErrors vndErrors = new VndError.VndErrors(new ArrayList<>());
            vndErrors.addError(new VndError.Error(FORBIDDEN_MESSAGE));

            final VndError response = new VndError();
            response.setEmbedded(vndErrors);

            return format(response);
        } catch (Throwable t) {
            return internalServerError();
        }
    }

    public String internalServerError() {
        try {
            final VndError.VndErrors vndErrors = new VndError.VndErrors(new ArrayList<>());
            vndErrors.addError(new VndError.Error(INTERNAL_ERROR_MESSAGE));

            final VndError response = new VndError();
            response.setEmbedded(vndErrors);

            return format(response);
        } catch (Throwable t) {
            return "{ " +
                "\"total\" : 1, " +
                "\"_embedded\": { " +
                    "\"errors\": [" +
                        "{ \"message\": \"Internal Server Error\", \"path\": \"/\"}" +
                    "]" +
                "}" +
            "}";
        }
    }

    private String format(final Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
