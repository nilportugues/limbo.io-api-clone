package io.bandit.limbo.limbo.application.api.resources.city;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.city.queries.GetCity;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.application.api.resources.city.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.city.metrics.CityApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.city.middleware.CityQueryParams;
import io.bandit.limbo.limbo.application.api.resources.city.presenters.CityOutputBoundary;
import io.bandit.limbo.limbo.application.api.resources.city.routing.CityHttpRouter;
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
public class GetCityResource {

    private static final String SWAGGER_DOC_TAG = "City";
    private static final String SWAGGER_DOC_GET = "Get a city by ID.";

    private final IQueryBus queryBus;
    private final CityOutputBoundary presenter;
    private final CityApiMetrics metrics;

    @Inject
    public GetCityResource(final IQueryBus queryBus,
            @Named("CityJsonPresenter") final CityOutputBoundary presenter,
            final CityApiMetrics metrics) {

        this.presenter = presenter;
        this.queryBus = queryBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = CityHttpRouter.CITY_ONE_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = CityResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CityUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CityForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = CityNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = CityInternalServerError.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable final String id) {

        return Mono.fromFuture(queryBus.dispatch(new GetCity.Query(id)))
            .map(city -> this.okView((City) city))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementCityError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> okView(final City city) {
        metrics.incrementCitySuccess();
        return new ResponseEntity<>(presenter.success(city), HttpStatus.OK);
    }

    private ResponseEntity<String> notFoundView(final String cityId) {
        metrics.incrementCityNotFound();
        return new ResponseEntity<>(presenter.notFound(cityId), HttpStatus.NOT_FOUND);
    }
}
