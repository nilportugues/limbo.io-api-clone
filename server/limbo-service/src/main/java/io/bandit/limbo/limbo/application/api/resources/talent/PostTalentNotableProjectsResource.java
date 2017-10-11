package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.talent.commands.TalentNotableProjectsPersist;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.notableprojects.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.notableprojects.middleware.NotableProjectsQueryParams;
import io.bandit.limbo.limbo.application.api.resources.notableprojects.presenters.NotableProjectsOutputBoundary;
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
public class PostTalentNotableProjectsResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_POST_MANY_NOTABLE_PROJECTS = "Creates notable projects for the current Talents.";

    private final NotableProjectsQueryParams queryParams;
    private final NotableProjectsOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final TalentApiMetrics metrics;

    @Inject
    public PostTalentNotableProjectsResource(
        final ICommandBus commandBus,
        final NotableProjectsQueryParams queryParams,
        @Named("NotableProjectsJsonPresenter") final NotableProjectsOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.queryParams = queryParams;
        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_POST_MANY_NOTABLE_PROJECTS, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_NOTABLEPROJECTS_ROUTE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Created", response = NotableProjectsResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = NotableProjectsUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = NotableProjectsForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = NotableProjectsNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = NotableProjectsInternalServerError.class)
    })
    public Future<ResponseEntity<String>> create(@PathVariable String id, @RequestBody final NotableProjectsData data) {

        final TalentNotableProjectsPersist.Command command = new TalentNotableProjectsPersist.Command(id, NotableProjectsData.toNotableProjects(data));

        return Mono.fromFuture(commandBus.execute(command))
            .map(notableProjects -> createdView((NotableProjects) notableProjects))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private ResponseEntity<String> createdView(final NotableProjects notableProjects) {
        metrics.incrementTalentNotableProjectsCreated();
        return new ResponseEntity<>(presenter.success(notableProjects), HttpStatus.CREATED);
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentNotableProjectsError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentNotableProjectsNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }
}
