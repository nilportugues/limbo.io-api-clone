package io.bandit.limbo.limbo.application.api.resources.talentrole;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.talentrole.queries.GetTalentRole;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.application.api.resources.talentrole.middleware.TalentRoleQueryParams;
import io.bandit.limbo.limbo.application.api.resources.talentrole.presenters.TalentRoleJsonPresenter;
import io.bandit.limbo.limbo.application.api.resources.talentrole.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talentrole.metrics.TalentRoleApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talentrole.routing.TalentRoleHttpRouter;
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

// _EntityResource.java
@RestController
public class GetTalentRoleResource {

    private static final String SWAGGER_DOC_TAG = "Talent Role";
    private static final String SWAGGER_DOC_GET = "Get a Talent Role by ID.";

    private final IQueryBus queryBus;
    private final TalentRoleJsonPresenter presenter;
    private final TalentRoleApiMetrics metrics;

    @Inject
    public GetTalentRoleResource(final IQueryBus queryBus,
                                         final TalentRoleJsonPresenter presenter,
                                         final TalentRoleApiMetrics metrics) {
        this.queryBus = queryBus;
        this.presenter = presenter;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentRoleHttpRouter.TALENTROLE_ONE_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = TalentRoleResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TalentRoleUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TalentRoleForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = TalentRoleNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TalentRoleInternalServerError.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable final String id) {

        return Mono.fromFuture(queryBus.dispatch(new GetTalentRole.Query(id)))
            .map(talentRole -> this.okView((TalentRole) talentRole))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentRoleError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> okView(final TalentRole talentRole) {
        metrics.incrementTalentRoleSuccess();
        return new ResponseEntity<>(presenter.success(talentRole), HttpStatus.OK);
    }

    private ResponseEntity<String> notFoundView(final String talentRoleId) {
        metrics.incrementTalentRoleNotFound();
        return new ResponseEntity<>(presenter.notFound(talentRoleId), HttpStatus.NOT_FOUND);
    }
}
