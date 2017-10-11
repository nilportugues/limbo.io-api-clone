package io.bandit.limbo.limbo.application.api.resources.notableprojects;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.notableprojects.queries.GetNotableProjects;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.application.api.resources.notableprojects.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.notableprojects.metrics.NotableProjectsApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.notableprojects.middleware.NotableProjectsQueryParams;
import io.bandit.limbo.limbo.application.api.resources.notableprojects.presenters.NotableProjectsOutputBoundary;
import io.bandit.limbo.limbo.application.api.resources.notableprojects.routing.NotableProjectsHttpRouter;
import io.bandit.limbo.limbo.modules.shared.model.*;
import io.bandit.limbo.limbo.modules.shared.middleware.PaginationUtil;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Links;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Future;
import java.net.URI;
import java.util.Optional;

// _AggregateResource.java
@RestController
public class GetNotableProjectsResource {

    private static final String SWAGGER_DOC_TAG = "Notable Projects";
    private static final String SWAGGER_DOC_GET = "Get a notable projects by ID.";

    private final IQueryBus queryBus;
    private final NotableProjectsOutputBoundary presenter;
    private final NotableProjectsApiMetrics metrics;

    @Inject
    public GetNotableProjectsResource(final IQueryBus queryBus,
            @Named("NotableProjectsJsonPresenter") final NotableProjectsOutputBoundary presenter,
            final NotableProjectsApiMetrics metrics) {

        this.presenter = presenter;
        this.queryBus = queryBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = NotableProjectsHttpRouter.NOTABLEPROJECTS_ONE_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = NotableProjectsResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = NotableProjectsUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = NotableProjectsForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = NotableProjectsNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = NotableProjectsInternalServerError.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable final String id) {

        return Mono.fromFuture(queryBus.dispatch(new GetNotableProjects.Query(id)))
            .map(notableProjects -> this.okView((NotableProjects) notableProjects))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementNotableProjectsError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> okView(final NotableProjects notableProjects) {
        metrics.incrementNotableProjectsSuccess();
        return new ResponseEntity<>(presenter.success(notableProjects), HttpStatus.OK);
    }

    private ResponseEntity<String> notFoundView(final String notableProjectsId) {
        metrics.incrementNotableProjectsNotFound();
        return new ResponseEntity<>(presenter.notFound(notableProjectsId), HttpStatus.NOT_FOUND);
    }
}
