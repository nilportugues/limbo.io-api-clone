package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.talent.commands.TalentSkillsDelete;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.skills.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.skills.middleware.SkillsQueryParams;
import io.bandit.limbo.limbo.application.api.resources.skills.presenters.SkillsOutputBoundary;
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
public class DeleteTalentSkillsResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_DELETE_MANY_SKILLS = "Removes the talents for the current skills.";

    private final SkillsOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final TalentApiMetrics metrics;

    @Inject
    public DeleteTalentSkillsResource(
        final ICommandBus commandBus,
        @Named("SkillsJsonPresenter") final SkillsOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_DELETE_MANY_SKILLS, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_SKILLS_ROUTE, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 401, message = "Unauthorized", response = SkillsUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SkillsForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = SkillsNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = SkillsInternalServerError.class)
    })
    public Future<ResponseEntity<String>> delete(@PathVariable String id) {

        return Mono.fromFuture(commandBus.execute(new TalentSkillsDelete.Command(id)))
            .map(talent -> this.deleteView((Talent) talent))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }


    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentSkillsError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentSkillsNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> deleteView(final Talent talent) {
        metrics.incrementTalentSkillsDeleted();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
