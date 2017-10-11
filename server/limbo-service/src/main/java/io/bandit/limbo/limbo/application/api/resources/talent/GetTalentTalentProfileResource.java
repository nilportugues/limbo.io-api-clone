package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.talent.queries.TalentTalentProfile;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.application.api.resources.talent.marshallers.*;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.talentprofile.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.metrics.TalentApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.talentprofile.presenters.TalentProfileOutputBoundary;
import io.bandit.limbo.limbo.modules.shared.model.*;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.Future;

// _EntityRelationshipResourceOneToMany.java
@RestController
public class GetTalentTalentProfileResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_GET_ONE_TALENT_PROFILES= "Gets the TalentProfile for the current Talents.";

    private final IQueryBus queryBus;
    private final TalentProfileOutputBoundary presenter;
    private final TalentApiMetrics metrics;

    @Inject
    public GetTalentTalentProfileResource(final IQueryBus queryBus,
        @Named("TalentProfileJsonPresenter") final TalentProfileOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.presenter = presenter;
        this.queryBus = queryBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET_ONE_TALENT_PROFILES, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_TALENTPROFILE_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = TalentProfileResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TalentProfileUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TalentProfileForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = TalentProfileNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TalentProfileInternalServerError.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable final String id) {

        return Mono.fromFuture(queryBus.dispatch(new TalentTalentProfile.Query(id)))
            .map(v -> ((Talent) v).getTalentProfile())
            .map(this::okView)
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private ResponseEntity<String> okView(final TalentProfile talentProfile) {
        metrics.incrementTalentTalentProfileSuccess();
        return new ResponseEntity<>(presenter.success(talentProfile), HttpStatus.OK);
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentTalentProfileNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentTalentProfileError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
