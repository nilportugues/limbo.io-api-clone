package io.bandit.limbo.limbo.application.api.resources.talent;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.talent.commands.TalentCityPersist;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.application.api.resources.talent.marshallers.*;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.city.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.talent.metrics.TalentApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.city.presenters.CityOutputBoundary;
import io.bandit.limbo.limbo.modules.shared.model.*;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

// _EntityRelationshipResourceOneToMany.java
@RestController
public class PutTalentCityResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_PUT_ONE_CITIES = "Replaces a relationship between talents with a new city.";

    private final CityOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final TalentApiMetrics metrics;

    @Inject
    public PutTalentCityResource(
        final ICommandBus commandBus,
        @Named("CityJsonPresenter") final CityOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_PUT_ONE_CITIES, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_CITY_ROUTE, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = CityResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CityUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CityForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = CityNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = CityInternalServerError.class)
    })
    public Future<ResponseEntity<String>> update(@PathVariable String id, @RequestBody final CityData data) {

final TalentCityPersist.Command command = new TalentCityPersist.Command(id, CityData.toCity(data));

        return Mono.fromFuture(commandBus.execute(command))
            .map(talent -> ((Talent) talent).getCity())
            .map(this::updatedView)
            .publishOn(Schedulers.parallel())
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentCityNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentCityError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> updatedView(final City city) {
        metrics.incrementTalentCityUpdated();
        return new ResponseEntity<>(presenter.success(city), HttpStatus.OK);
    }
}
