package io.bandit.limbo.limbo.application.api.resources.country;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.country.commands.CountryDelete;
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
public class DeleteCountryResource {

    private static final String SWAGGER_DOC_TAG = "Country";
    private static final String SWAGGER_DOC_DELETE = "Delete a country by ID.";

    private final CountryOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final CountryApiMetrics metrics;

    @Inject
    public DeleteCountryResource(final ICommandBus commandBus,
        @Named("CountryJsonPresenter") final CountryOutputBoundary presenter,
        final CountryApiMetrics metrics) {

        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_DELETE, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value =  CountryHttpRouter.COUNTRY_ONE_ROUTE, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 401, message = "Unauthorized", response = CountryUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CountryForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = CountryNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = CountryInternalServerError.class)
    })
    public Future<ResponseEntity<String>> delete(@PathVariable final String id) {

        return Mono.fromFuture(commandBus.execute(new CountryDelete.Command(id)))
            .map(country -> this.deleteView((Country) country))
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
    private ResponseEntity<String> deleteView(final Country country) {
        metrics.incrementCountryDeleted();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
