package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.main.talent.commands.TalentTalentExperiencePersist;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.talentexperience.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.talent.metrics.TalentApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talentexperience.presenters.TalentExperienceOutputBoundary;
import io.swagger.annotations.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.Future;

// _EntityRelationshipResourceOneToMany.java
@RestController
public class PostTalentTalentExperienceResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_POST_ONE_TALENT_EXPERIENCES = "Creates a relationship between talents with a new talent experience.";

    private final TalentExperienceOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final TalentApiMetrics metrics;

    @Inject
    public PostTalentTalentExperienceResource(final ICommandBus commandBus,
        @Named("TalentExperienceJsonPresenter") final TalentExperienceOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_POST_ONE_TALENT_EXPERIENCES, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_TALENTEXPERIENCE_ROUTE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Created", response = TalentExperienceResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TalentExperienceUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TalentExperienceForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = TalentExperienceNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TalentExperienceInternalServerError.class)
    })
    public Future<ResponseEntity<String>> create(@PathVariable String id, @RequestBody final TalentExperienceData data) {

        final TalentTalentExperiencePersist.Command command = new TalentTalentExperiencePersist.Command(id, TalentExperienceData.toTalentExperience(data));

        return Mono.fromFuture(commandBus.execute(command))
            .map(talent -> ((Talent) talent).getTalentExperience())
            .map(this::createdView)
            .publishOn(Schedulers.parallel())
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentTalentExperienceNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentTalentExperienceError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> createdView(final TalentExperience talentExperience) {
        metrics.incrementTalentTalentExperienceCreated();
        return new ResponseEntity<>(presenter.success(talentExperience), HttpStatus.CREATED);
    }
}
