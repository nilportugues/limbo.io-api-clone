package io.bandit.limbo.limbo.application.api.resources.personaltraits;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.personaltraits.queries.GetPersonalTraits;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.application.api.resources.personaltraits.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.personaltraits.metrics.PersonalTraitsApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.personaltraits.middleware.PersonalTraitsQueryParams;
import io.bandit.limbo.limbo.application.api.resources.personaltraits.presenters.PersonalTraitsOutputBoundary;
import io.bandit.limbo.limbo.application.api.resources.personaltraits.routing.PersonalTraitsHttpRouter;
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
public class GetPersonalTraitsResource {

    private static final String SWAGGER_DOC_TAG = "Personal Traits";
    private static final String SWAGGER_DOC_GET = "Get a personal traits by ID.";

    private final IQueryBus queryBus;
    private final PersonalTraitsOutputBoundary presenter;
    private final PersonalTraitsApiMetrics metrics;

    @Inject
    public GetPersonalTraitsResource(final IQueryBus queryBus,
            @Named("PersonalTraitsJsonPresenter") final PersonalTraitsOutputBoundary presenter,
            final PersonalTraitsApiMetrics metrics) {

        this.presenter = presenter;
        this.queryBus = queryBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = PersonalTraitsHttpRouter.PERSONALTRAITS_ONE_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = PersonalTraitsResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = PersonalTraitsUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = PersonalTraitsForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = PersonalTraitsNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = PersonalTraitsInternalServerError.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable final String id) {

        return Mono.fromFuture(queryBus.dispatch(new GetPersonalTraits.Query(id)))
            .map(personalTraits -> this.okView((PersonalTraits) personalTraits))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementPersonalTraitsError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> okView(final PersonalTraits personalTraits) {
        metrics.incrementPersonalTraitsSuccess();
        return new ResponseEntity<>(presenter.success(personalTraits), HttpStatus.OK);
    }

    private ResponseEntity<String> notFoundView(final String personalTraitsId) {
        metrics.incrementPersonalTraitsNotFound();
        return new ResponseEntity<>(presenter.notFound(personalTraitsId), HttpStatus.NOT_FOUND);
    }
}
