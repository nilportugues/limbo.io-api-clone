package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.talent.commands.TalentSkillsPersist;
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
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.Future;

@RestController
public class PostTalentSkillsResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_POST_MANY_SKILLS = "Creates skills for the current Talents.";

    private final SkillsQueryParams queryParams;
    private final SkillsOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final TalentApiMetrics metrics;

    @Inject
    public PostTalentSkillsResource(
        final ICommandBus commandBus,
        final SkillsQueryParams queryParams,
        @Named("SkillsJsonPresenter") final SkillsOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.queryParams = queryParams;
        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_POST_MANY_SKILLS, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_SKILLS_ROUTE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Created", response = SkillsResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SkillsUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SkillsForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = SkillsNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = SkillsInternalServerError.class)
    })
    public Future<ResponseEntity<String>> create(@PathVariable String id, @RequestBody final SkillsData data) {

        final TalentSkillsPersist.Command command = new TalentSkillsPersist.Command(id, SkillsData.toSkills(data));

        return Mono.fromFuture(commandBus.execute(command))
            .map(skills -> createdView((Skills) skills))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private ResponseEntity<String> createdView(final Skills skills) {
        metrics.incrementTalentSkillsCreated();
        return new ResponseEntity<>(presenter.success(skills), HttpStatus.CREATED);
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentSkillsError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentSkillsNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }
}
