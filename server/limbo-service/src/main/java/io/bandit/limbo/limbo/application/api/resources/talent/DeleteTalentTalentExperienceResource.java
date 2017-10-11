package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.talent.commands.TalentTalentExperienceDelete;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.application.api.resources.talent.marshallers.*;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.talentexperience.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.talentexperience.presenters.TalentExperienceOutputBoundary;
import io.bandit.limbo.limbo.application.api.resources.talent.metrics.TalentApiMetrics;
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
public class DeleteTalentTalentExperienceResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_DELETE_ONE_TALENT_EXPERIENCES = "Removes the talents for the current talent experience.";

    private final TalentExperienceOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final TalentApiMetrics metrics;

    @Inject
    public DeleteTalentTalentExperienceResource(
        final ICommandBus commandBus,
        @Named("TalentExperienceJsonPresenter") final TalentExperienceOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_DELETE_ONE_TALENT_EXPERIENCES, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_TALENTEXPERIENCE_ROUTE, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 401, message = "Unauthorized", response = TalentExperienceUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TalentExperienceForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = TalentExperienceNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TalentExperienceInternalServerError.class)
    })
    public Future<ResponseEntity<String>> delete(@PathVariable final String id) {

        return Mono.fromFuture(commandBus.execute(new TalentTalentExperienceDelete.Command(id)))
            .map(talent -> this.deleteView((Talent) talent))
            .defaultIfEmpty(notFoundView(id))
            .doOnError(this::errorView)
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

    private ResponseEntity<String> deleteView(final Talent talent) {
        metrics.incrementTalentTalentExperienceDeleted();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
