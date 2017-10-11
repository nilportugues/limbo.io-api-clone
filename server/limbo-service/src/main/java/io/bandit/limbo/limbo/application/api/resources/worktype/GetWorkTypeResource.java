package io.bandit.limbo.limbo.application.api.resources.worktype;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.worktype.queries.GetWorkType;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.application.api.resources.worktype.middleware.WorkTypeQueryParams;
import io.bandit.limbo.limbo.application.api.resources.worktype.presenters.WorkTypeJsonPresenter;
import io.bandit.limbo.limbo.application.api.resources.worktype.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.worktype.metrics.WorkTypeApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.worktype.routing.WorkTypeHttpRouter;
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
public class GetWorkTypeResource {

    private static final String SWAGGER_DOC_TAG = "Work Type";
    private static final String SWAGGER_DOC_GET = "Get a Work Type by ID.";

    private final IQueryBus queryBus;
    private final WorkTypeJsonPresenter presenter;
    private final WorkTypeApiMetrics metrics;

    @Inject
    public GetWorkTypeResource(final IQueryBus queryBus,
                                         final WorkTypeJsonPresenter presenter,
                                         final WorkTypeApiMetrics metrics) {
        this.queryBus = queryBus;
        this.presenter = presenter;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = WorkTypeHttpRouter.WORKTYPE_ONE_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = WorkTypeResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = WorkTypeUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = WorkTypeForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = WorkTypeNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = WorkTypeInternalServerError.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable final String id) {

        return Mono.fromFuture(queryBus.dispatch(new GetWorkType.Query(id)))
            .map(workType -> this.okView((WorkType) workType))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementWorkTypeError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> okView(final WorkType workType) {
        metrics.incrementWorkTypeSuccess();
        return new ResponseEntity<>(presenter.success(workType), HttpStatus.OK);
    }

    private ResponseEntity<String> notFoundView(final String workTypeId) {
        metrics.incrementWorkTypeNotFound();
        return new ResponseEntity<>(presenter.notFound(workTypeId), HttpStatus.NOT_FOUND);
    }
}
