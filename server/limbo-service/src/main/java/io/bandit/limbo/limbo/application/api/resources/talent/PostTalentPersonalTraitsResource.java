package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.talent.commands.TalentPersonalTraitsPersist;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.personaltraits.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.personaltraits.middleware.PersonalTraitsQueryParams;
import io.bandit.limbo.limbo.application.api.resources.personaltraits.presenters.PersonalTraitsOutputBoundary;
import io.bandit.limbo.limbo.application.api.resources.talent.metrics.TalentApiMetrics;
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
public class PostTalentPersonalTraitsResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_POST_MANY_PERSONAL_TRAITS = "Creates personal traits for the current Talents.";

    private final PersonalTraitsQueryParams queryParams;
    private final PersonalTraitsOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final TalentApiMetrics metrics;

    @Inject
    public PostTalentPersonalTraitsResource(
        final ICommandBus commandBus,
        final PersonalTraitsQueryParams queryParams,
        @Named("PersonalTraitsJsonPresenter") final PersonalTraitsOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.queryParams = queryParams;
        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_POST_MANY_PERSONAL_TRAITS, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_PERSONALTRAITS_ROUTE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Created", response = PersonalTraitsResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = PersonalTraitsUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = PersonalTraitsForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = PersonalTraitsNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = PersonalTraitsInternalServerError.class)
    })
    public Future<ResponseEntity<String>> create(@PathVariable String id, @RequestBody final PersonalTraitsData data) {

        final TalentPersonalTraitsPersist.Command command = new TalentPersonalTraitsPersist.Command(id, PersonalTraitsData.toPersonalTraits(data));

        return Mono.fromFuture(commandBus.execute(command))
            .map(personalTraits -> createdView((PersonalTraits) personalTraits))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private ResponseEntity<String> createdView(final PersonalTraits personalTraits) {
        metrics.incrementTalentPersonalTraitsCreated();
        return new ResponseEntity<>(presenter.success(personalTraits), HttpStatus.CREATED);
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentPersonalTraitsError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentPersonalTraitsNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }
}
