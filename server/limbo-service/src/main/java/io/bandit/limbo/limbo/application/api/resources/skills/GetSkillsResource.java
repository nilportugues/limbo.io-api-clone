package io.bandit.limbo.limbo.application.api.resources.skills;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.skills.queries.GetSkills;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.application.api.resources.skills.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.skills.metrics.SkillsApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.skills.middleware.SkillsQueryParams;
import io.bandit.limbo.limbo.application.api.resources.skills.presenters.SkillsOutputBoundary;
import io.bandit.limbo.limbo.application.api.resources.skills.routing.SkillsHttpRouter;
import io.bandit.limbo.limbo.modules.shared.model.*;
import io.bandit.limbo.limbo.modules.shared.middleware.PaginationUtil;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Links;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
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
public class GetSkillsResource {

    private static final String SWAGGER_DOC_TAG = "Skills";
    private static final String SWAGGER_DOC_GET = "Get a skills by ID.";

    private final IQueryBus queryBus;
    private final SkillsOutputBoundary presenter;
    private final SkillsApiMetrics metrics;

    @Inject
    public GetSkillsResource(final IQueryBus queryBus,
            @Named("SkillsJsonPresenter") final SkillsOutputBoundary presenter,
            final SkillsApiMetrics metrics) {

        this.presenter = presenter;
        this.queryBus = queryBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = SkillsHttpRouter.SKILLS_ONE_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = SkillsResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SkillsUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SkillsForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = SkillsNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = SkillsInternalServerError.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable final String id) {

        return Mono.fromFuture(queryBus.dispatch(new GetSkills.Query(id)))
            .map(skills -> this.okView((Skills) skills))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementSkillsError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> okView(final Skills skills) {
        metrics.incrementSkillsSuccess();
        return new ResponseEntity<>(presenter.success(skills), HttpStatus.OK);
    }

    private ResponseEntity<String> notFoundView(final String skillsId) {
        metrics.incrementSkillsNotFound();
        return new ResponseEntity<>(presenter.notFound(skillsId), HttpStatus.NOT_FOUND);
    }
}
