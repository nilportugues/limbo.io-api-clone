package io.bandit.limbo.limbo.application.api.resources.country;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.country.commands.CountryPersist;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.application.api.resources.country.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.country.middleware.CountryQueryParams;
import io.bandit.limbo.limbo.application.api.resources.country.presenters.CountryOutputBoundary;
import io.bandit.limbo.limbo.application.api.resources.country.routing.CountryHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.country.metrics.CountryApiMetrics;
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
import java.util.concurrent.Future;

// _AggregateResource.java
@RestController
public class PostCountryResource {

    private static final String SWAGGER_DOC_TAG = "Country";
    private static final String SWAGGER_DOC_CREATE = "Data a new country.";

    private final CountryOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final CountryApiMetrics metrics;

    @Inject
    public PostCountryResource(final ICommandBus commandBus,
                @Named("CountryJsonPresenter") final CountryOutputBoundary presenter,
                final CountryApiMetrics metrics) {

        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_CREATE, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = CountryHttpRouter.COUNTRY_MANY_ROUTE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Created", response = CountryResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CountryUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CountryForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = CountryNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = CountryInternalServerError.class)
    })
    public Future<ResponseEntity<String>> create(@RequestBody final CountryData data) {

        return Mono.fromFuture(commandBus.execute(new CountryPersist.Command(CountryData.toCountry(data))))
                .publishOn(Schedulers.parallel())
                .map(country -> this.createdView((Country) country))
                .doOnError(this::errorView)
                .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementCountryError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> createdView(final Country country) {
        metrics.incrementCountryCreated();
        return new ResponseEntity<>(presenter.success(country), HttpStatus.CREATED);
    }
}
