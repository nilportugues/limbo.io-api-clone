package io.bandit.limbo.limbo.application.api.resources.talentprofile;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.talentprofile.queries.GetTalentProfile;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.application.api.resources.talentprofile.middleware.TalentProfileQueryParams;
import io.bandit.limbo.limbo.application.api.resources.talentprofile.presenters.TalentProfileJsonPresenter;
import io.bandit.limbo.limbo.application.api.resources.talentprofile.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talentprofile.metrics.TalentProfileApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talentprofile.routing.TalentProfileHttpRouter;
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
public class GetTalentProfileResource {

    private static final String SWAGGER_DOC_TAG = "Talent Profile";
    private static final String SWAGGER_DOC_GET = "Get a Talent Profile by ID.";

    private final IQueryBus queryBus;
    private final TalentProfileJsonPresenter presenter;
    private final TalentProfileApiMetrics metrics;

    @Inject
    public GetTalentProfileResource(final IQueryBus queryBus,
                                         final TalentProfileJsonPresenter presenter,
                                         final TalentProfileApiMetrics metrics) {
        this.queryBus = queryBus;
        this.presenter = presenter;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentProfileHttpRouter.TALENTPROFILE_ONE_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = TalentProfileResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TalentProfileUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TalentProfileForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = TalentProfileNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TalentProfileInternalServerError.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable final String id) {

        return Mono.fromFuture(queryBus.dispatch(new GetTalentProfile.Query(id)))
            .map(talentProfile -> this.okView((TalentProfile) talentProfile))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentProfileError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> okView(final TalentProfile talentProfile) {
        metrics.incrementTalentProfileSuccess();
        return new ResponseEntity<>(presenter.success(talentProfile), HttpStatus.OK);
    }

    private ResponseEntity<String> notFoundView(final String talentProfileId) {
        metrics.incrementTalentProfileNotFound();
        return new ResponseEntity<>(presenter.notFound(talentProfileId), HttpStatus.NOT_FOUND);
    }
}
