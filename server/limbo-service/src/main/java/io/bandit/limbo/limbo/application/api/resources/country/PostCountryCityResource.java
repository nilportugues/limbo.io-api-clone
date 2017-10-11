package io.bandit.limbo.limbo.application.api.resources.country;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.country.commands.CountryCityPersist;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.application.api.resources.city.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.country.routing.CountryHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.city.middleware.CityQueryParams;
import io.bandit.limbo.limbo.application.api.resources.city.presenters.CityOutputBoundary;
import io.bandit.limbo.limbo.application.api.resources.country.metrics.CountryApiMetrics;
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

@RestController
public class PostCountryCityResource {

    private static final String SWAGGER_DOC_TAG = "Country";
    private static final String SWAGGER_DOC_POST_MANY_CITIES = "Creates city for the current Countries.";

    private final CityQueryParams queryParams;
    private final CityOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final CountryApiMetrics metrics;

    @Inject
    public PostCountryCityResource(
        final ICommandBus commandBus,
        final CityQueryParams queryParams,
        @Named("CityJsonPresenter") final CityOutputBoundary presenter,
        final CountryApiMetrics metrics) {

        this.queryParams = queryParams;
        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_POST_MANY_CITIES, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = CountryHttpRouter.COUNTRY_CITY_ROUTE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Created", response = CityResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CityUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CityForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = CityNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = CityInternalServerError.class)
    })
    public Future<ResponseEntity<String>> create(@PathVariable String id, @RequestBody final CityData data) {

        final CountryCityPersist.Command command = new CountryCityPersist.Command(id, CityData.toCity(data));

        return Mono.fromFuture(commandBus.execute(command))
            .map(city -> createdView((City) city))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private ResponseEntity<String> createdView(final City city) {
        metrics.incrementCountryCityCreated();
        return new ResponseEntity<>(presenter.success(city), HttpStatus.CREATED);
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementCountryCityError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> notFoundView(final String countryId) {
        metrics.incrementCountryCityNotFound();
        return new ResponseEntity<>(presenter.notFound(countryId), HttpStatus.NOT_FOUND);
    }
}
