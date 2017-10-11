package io.bandit.limbo.limbo.application.api.resources.socialnetworks;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.socialnetworks.queries.GetSocialNetworks;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.application.api.resources.socialnetworks.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.socialnetworks.metrics.SocialNetworksApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.socialnetworks.middleware.SocialNetworksQueryParams;
import io.bandit.limbo.limbo.application.api.resources.socialnetworks.presenters.SocialNetworksOutputBoundary;
import io.bandit.limbo.limbo.application.api.resources.socialnetworks.routing.SocialNetworksHttpRouter;
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
public class GetSocialNetworksResource {

    private static final String SWAGGER_DOC_TAG = "Social Networks";
    private static final String SWAGGER_DOC_GET = "Get a social networks by ID.";

    private final IQueryBus queryBus;
    private final SocialNetworksOutputBoundary presenter;
    private final SocialNetworksApiMetrics metrics;

    @Inject
    public GetSocialNetworksResource(final IQueryBus queryBus,
            @Named("SocialNetworksJsonPresenter") final SocialNetworksOutputBoundary presenter,
            final SocialNetworksApiMetrics metrics) {

        this.presenter = presenter;
        this.queryBus = queryBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = SocialNetworksHttpRouter.SOCIALNETWORKS_ONE_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = SocialNetworksResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SocialNetworksUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SocialNetworksForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = SocialNetworksNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = SocialNetworksInternalServerError.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable final String id) {

        return Mono.fromFuture(queryBus.dispatch(new GetSocialNetworks.Query(id)))
            .map(socialNetworks -> this.okView((SocialNetworks) socialNetworks))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementSocialNetworksError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> okView(final SocialNetworks socialNetworks) {
        metrics.incrementSocialNetworksSuccess();
        return new ResponseEntity<>(presenter.success(socialNetworks), HttpStatus.OK);
    }

    private ResponseEntity<String> notFoundView(final String socialNetworksId) {
        metrics.incrementSocialNetworksNotFound();
        return new ResponseEntity<>(presenter.notFound(socialNetworksId), HttpStatus.NOT_FOUND);
    }
}
