package io.bandit.limbo.limbo.application.api.resources.talenttitle;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.talenttitle.queries.GetTalentTitle;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.application.api.resources.talenttitle.middleware.TalentTitleQueryParams;
import io.bandit.limbo.limbo.application.api.resources.talenttitle.presenters.TalentTitleJsonPresenter;
import io.bandit.limbo.limbo.application.api.resources.talenttitle.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talenttitle.metrics.TalentTitleApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talenttitle.routing.TalentTitleHttpRouter;
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
public class GetTalentTitleResource {

    private static final String SWAGGER_DOC_TAG = "Talent Title";
    private static final String SWAGGER_DOC_GET = "Get a Talent Title by ID.";

    private final IQueryBus queryBus;
    private final TalentTitleJsonPresenter presenter;
    private final TalentTitleApiMetrics metrics;

    @Inject
    public GetTalentTitleResource(final IQueryBus queryBus,
                                         final TalentTitleJsonPresenter presenter,
                                         final TalentTitleApiMetrics metrics) {
        this.queryBus = queryBus;
        this.presenter = presenter;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentTitleHttpRouter.TALENTTITLE_ONE_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = TalentTitleResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TalentTitleUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TalentTitleForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = TalentTitleNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TalentTitleInternalServerError.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable final String id) {

        return Mono.fromFuture(queryBus.dispatch(new GetTalentTitle.Query(id)))
            .map(talentTitle -> this.okView((TalentTitle) talentTitle))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentTitleError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> okView(final TalentTitle talentTitle) {
        metrics.incrementTalentTitleSuccess();
        return new ResponseEntity<>(presenter.success(talentTitle), HttpStatus.OK);
    }

    private ResponseEntity<String> notFoundView(final String talentTitleId) {
        metrics.incrementTalentTitleNotFound();
        return new ResponseEntity<>(presenter.notFound(talentTitleId), HttpStatus.NOT_FOUND);
    }
}
