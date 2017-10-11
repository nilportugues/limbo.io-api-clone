package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.talent.queries.TalentTalentTitle;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.application.api.resources.talent.marshallers.*;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.talenttitle.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.metrics.TalentApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.talenttitle.presenters.TalentTitleOutputBoundary;
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
public class GetTalentTalentTitleResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_GET_ONE_TALENT_TITLES= "Gets the TalentTitle for the current Talents.";

    private final IQueryBus queryBus;
    private final TalentTitleOutputBoundary presenter;
    private final TalentApiMetrics metrics;

    @Inject
    public GetTalentTalentTitleResource(final IQueryBus queryBus,
        @Named("TalentTitleJsonPresenter") final TalentTitleOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.presenter = presenter;
        this.queryBus = queryBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET_ONE_TALENT_TITLES, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_TALENTTITLE_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = TalentTitleResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TalentTitleUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TalentTitleForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = TalentTitleNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TalentTitleInternalServerError.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable final String id) {

        return Mono.fromFuture(queryBus.dispatch(new TalentTalentTitle.Query(id)))
            .map(v -> ((Talent) v).getTalentTitle())
            .map(this::okView)
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private ResponseEntity<String> okView(final TalentTitle talentTitle) {
        metrics.incrementTalentTalentTitleSuccess();
        return new ResponseEntity<>(presenter.success(talentTitle), HttpStatus.OK);
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentTalentTitleNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentTalentTitleError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
