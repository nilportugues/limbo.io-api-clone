package io.bandit.limbo.limbo.application.api.resources.country;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.country.commands.CountryPersist;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.application.api.resources.country.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.country.middleware.CountryQueryParams;
import io.bandit.limbo.limbo.application.api.resources.country.presenters.CountryOutputBoundary;
import io.bandit.limbo.limbo.application.api.resources.country.metrics.CountryApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.country.routing.CountryHttpRouter;
import io.bandit.limbo.limbo.modules.shared.model.*;
import io.bandit.limbo.limbo.modules.shared.middleware.PaginationUtil;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Links;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
public class PutCountryResource {

    private static final String SWAGGER_DOC_TAG = "Country";
    private static final String SWAGGER_DOC_UPDATE = "Updates an existing country.";

    private final CountryOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final CountryApiMetrics metrics;

    @Inject
    public PutCountryResource(
        final ICommandBus commandBus,
        @Named("CountryJsonPresenter") final CountryOutputBoundary presenter,
        final CountryApiMetrics metrics) {

        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_UPDATE, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = CountryHttpRouter.COUNTRY_ONE_ROUTE, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = CountryResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CountryUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CountryForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = CountryNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = CountryInternalServerError.class)
    })
    public Future<ResponseEntity<String>> update(@PathVariable final String id,
                                                @RequestBody final CountryData data) {

        return Mono.fromFuture(commandBus.execute(new CountryPersist.Command(CountryData.toCountry(data))))
                .publishOn(Schedulers.parallel())
                .map(country -> updatedView((Country) country))
                .doOnError(this::errorView)
                .defaultIfEmpty(notFoundView(id))
                .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementCountryError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> notFoundView(final String countryId) {
        metrics.incrementCountryNotFound();
        return new ResponseEntity<>(presenter.notFound(countryId), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> updatedView(final Country country) {
        metrics.incrementCountryUpdated();
        return new ResponseEntity<>(presenter.success(country), HttpStatus.OK);
    }

}
