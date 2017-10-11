package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.talent.queries.TalentCity;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.application.api.resources.talent.marshallers.*;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.city.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.metrics.TalentApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.city.presenters.CityOutputBoundary;
import io.bandit.limbo.limbo.modules.shared.model.*;
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

// _EntityRelationshipResourceOneToMany.java
@RestController
public class GetTalentCityResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_GET_ONE_CITIES= "Gets the City for the current Talents.";

    private final IQueryBus queryBus;
    private final CityOutputBoundary presenter;
    private final TalentApiMetrics metrics;

    @Inject
    public GetTalentCityResource(final IQueryBus queryBus,
        @Named("CityJsonPresenter") final CityOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.presenter = presenter;
        this.queryBus = queryBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET_ONE_CITIES, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_CITY_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = CityResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CityUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CityForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = CityNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = CityInternalServerError.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable final String id) {

        return Mono.fromFuture(queryBus.dispatch(new TalentCity.Query(id)))
            .map(v -> ((Talent) v).getCity())
            .map(this::okView)
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private ResponseEntity<String> okView(final City city) {
        metrics.incrementTalentCitySuccess();
        return new ResponseEntity<>(presenter.success(city), HttpStatus.OK);
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentCityNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentCityError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
