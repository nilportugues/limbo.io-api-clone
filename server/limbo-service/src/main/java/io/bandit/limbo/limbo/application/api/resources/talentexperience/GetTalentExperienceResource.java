package io.bandit.limbo.limbo.application.api.resources.talentexperience;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.talentexperience.queries.GetTalentExperience;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.application.api.resources.talentexperience.middleware.TalentExperienceQueryParams;
import io.bandit.limbo.limbo.application.api.resources.talentexperience.presenters.TalentExperienceJsonPresenter;
import io.bandit.limbo.limbo.application.api.resources.talentexperience.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talentexperience.metrics.TalentExperienceApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talentexperience.routing.TalentExperienceHttpRouter;
import io.bandit.limbo.limbo.modules.shared.model.*;
import io.bandit.limbo.limbo.modules.shared.middleware.PaginationUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Links;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.Future;

// _EntityResource.java
@RestController
public class GetTalentExperienceResource {

    private static final String SWAGGER_DOC_TAG = "Talent Experience";
    private static final String SWAGGER_DOC_GET = "Get a Talent Experience by ID.";

    private final IQueryBus queryBus;
    private final TalentExperienceJsonPresenter presenter;
    private final TalentExperienceApiMetrics metrics;

    @Inject
    public GetTalentExperienceResource(final IQueryBus queryBus,
                                         final TalentExperienceJsonPresenter presenter,
                                         final TalentExperienceApiMetrics metrics) {
        this.queryBus = queryBus;
        this.presenter = presenter;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentExperienceHttpRouter.TALENTEXPERIENCE_ONE_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = TalentExperienceResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TalentExperienceUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TalentExperienceForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = TalentExperienceNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TalentExperienceInternalServerError.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable final String id) {

        return Mono.fromFuture(queryBus.dispatch(new GetTalentExperience.Query(id)))
            .map(talentExperience -> this.okView((TalentExperience) talentExperience))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentExperienceError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> okView(final TalentExperience talentExperience) {
        metrics.incrementTalentExperienceSuccess();
        return new ResponseEntity<>(presenter.success(talentExperience), HttpStatus.OK);
    }

    private ResponseEntity<String> notFoundView(final String talentExperienceId) {
        metrics.incrementTalentExperienceNotFound();
        return new ResponseEntity<>(presenter.notFound(talentExperienceId), HttpStatus.NOT_FOUND);
    }
}
