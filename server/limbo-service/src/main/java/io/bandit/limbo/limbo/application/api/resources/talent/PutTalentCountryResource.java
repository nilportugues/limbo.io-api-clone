package io.bandit.limbo.limbo.application.api.resources.talent;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.talent.commands.TalentCountryPersist;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.application.api.resources.talent.marshallers.*;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.country.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.talent.metrics.TalentApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.country.presenters.CountryOutputBoundary;
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
public class PutTalentCountryResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_PUT_ONE_COUNTRIES = "Replaces a relationship between talents with a new country.";

    private final CountryOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final TalentApiMetrics metrics;

    @Inject
    public PutTalentCountryResource(
        final ICommandBus commandBus,
        @Named("CountryJsonPresenter") final CountryOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_PUT_ONE_COUNTRIES, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_COUNTRY_ROUTE, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = CountryResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CountryUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CountryForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = CountryNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = CountryInternalServerError.class)
    })
    public Future<ResponseEntity<String>> update(@PathVariable String id, @RequestBody final CountryData data) {

final TalentCountryPersist.Command command = new TalentCountryPersist.Command(id, CountryData.toCountry(data));

        return Mono.fromFuture(commandBus.execute(command))
            .map(talent -> ((Talent) talent).getCountry())
            .map(this::updatedView)
            .publishOn(Schedulers.parallel())
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentCountryNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentCountryError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> updatedView(final Country country) {
        metrics.incrementTalentCountryUpdated();
        return new ResponseEntity<>(presenter.success(country), HttpStatus.OK);
    }
}
