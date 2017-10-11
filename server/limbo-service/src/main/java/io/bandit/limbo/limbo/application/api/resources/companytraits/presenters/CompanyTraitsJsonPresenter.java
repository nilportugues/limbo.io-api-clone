package io.bandit.limbo.limbo.application.api.resources.companytraits.presenters;

import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.application.api.resources.companytraits.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.companytraits.routing.CompanyTraitsHttpRouter;
import io.bandit.limbo.limbo.application.api.presenters.vnderror.VndError;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import javax.inject.Named;

@Named("CompanyTraitsJsonPresenter")
public class CompanyTraitsJsonPresenter implements CompanyTraitsOutputBoundary {

    private static final String NOT_FOUND_MESSAGE = "CompanyTraits with id '%s' was not found.";
    private static final String UNAUTHORIZED_MESSAGE = "You are required to authenticate in order to access this resource.";
    private static final String FORBIDDEN_MESSAGE = "You are not authorized to access this resource.";
    private static final String INTERNAL_ERROR_MESSAGE = "We cannot resolve your request right now. Please try again later.";
    private static final String OUT_OF_BOUNDS_MESSAGE = "Could not return page number '%s' of size '%s' because no more results exist.";

    private final ObjectMapper objectMapper;
    private final CompanyTraitsHttpRouter router;

    public CompanyTraitsJsonPresenter(final ObjectMapper objectMapper, final CompanyTraitsHttpRouter router) {
        this.objectMapper = objectMapper;
        this.router = router;
    }

    public String success(final CompanyTraits companyTraits) {
        try {
            final CompanyTraitsResponse response = CompanyTraitsResponse.fromCompanyTraits(
                    companyTraits,
                    router.getCompanyTraitsRoute(),
                    router.getCompanyTraitsRoute(companyTraits.getId()));

            return format(response);
        } catch (Throwable t) {
            return internalServerError();
        }
    }


    public String notFound(final String companyTraitsId) {
        try {

            final String message = String.format(NOT_FOUND_MESSAGE, companyTraitsId);
            final VndError.VndErrors vndErrors = new VndError.VndErrors(new ArrayList<>());
            vndErrors.addError(new VndError.Error(message, "id"));

            final VndError response = new VndError();
            response.setEmbedded(vndErrors);

            return format(response);
        } catch (Throwable t) {
            return internalServerError();
        }
    }

    public String paginatedResponse(final String queryParams, final Paginated<CompanyTraits> page) {
        try {
            final CompanyTraitsPaginatedResponse response = new CompanyTraitsPaginatedResponse(
            router.getCompanyTraitsRoute(),
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
