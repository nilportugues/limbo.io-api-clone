package io.bandit.limbo.limbo.application.api.resources.country;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.country.commands.CountryCityDelete;
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
import org.springframework.transaction.annotation.Transactional;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// _EntityRelationshipResourceOneToMany.java
@RestController
public class DeleteCountryCityResource {

    private static final String SWAGGER_DOC_TAG = "Country";
    private static final String SWAGGER_DOC_DELETE_MANY_CITIES = "Removes the countries for the current city.";

    private final CityOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final CountryApiMetrics metrics;

    @Inject
    public DeleteCountryCityResource(
        final ICommandBus commandBus,
        @Named("CityJsonPresenter") final CityOutputBoundary presenter,
        final CountryApiMetrics metrics) {

        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_DELETE_MANY_CITIES, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = CountryHttpRouter.COUNTRY_CITY_ROUTE, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 401, message = "Unauthorized", response = CityUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CityForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = CityNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = CityInternalServerError.class)
    })
    public Future<ResponseEntity<String>> delete(@PathVariable String id) {

        return Mono.fromFuture(commandBus.execute(new CountryCityDelete.Command(id)))
            .map(country -> this.deleteView((Country) country))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }


    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementCountryCityError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> notFoundView(final String countryId) {
        metrics.incrementCountryCityNotFound();
        return new ResponseEntity<>(presenter.notFound(countryId), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> deleteView(final Country country) {
        metrics.incrementCountryCityDeleted();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
