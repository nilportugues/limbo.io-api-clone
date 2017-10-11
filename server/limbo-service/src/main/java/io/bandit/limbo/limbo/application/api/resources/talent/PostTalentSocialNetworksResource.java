package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.talent.commands.TalentSocialNetworksPersist;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.socialnetworks.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.socialnetworks.middleware.SocialNetworksQueryParams;
import io.bandit.limbo.limbo.application.api.resources.socialnetworks.presenters.SocialNetworksOutputBoundary;
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
public class PostTalentSocialNetworksResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_POST_MANY_SOCIAL_NETWORKS = "Creates social networks for the current Talents.";

    private final SocialNetworksQueryParams queryParams;
    private final SocialNetworksOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final TalentApiMetrics metrics;

    @Inject
    public PostTalentSocialNetworksResource(
        final ICommandBus commandBus,
        final SocialNetworksQueryParams queryParams,
        @Named("SocialNetworksJsonPresenter") final SocialNetworksOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.queryParams = queryParams;
        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_POST_MANY_SOCIAL_NETWORKS, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_SOCIALNETWORKS_ROUTE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Created", response = SocialNetworksResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SocialNetworksUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SocialNetworksForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = SocialNetworksNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = SocialNetworksInternalServerError.class)
    })
    public Future<ResponseEntity<String>> create(@PathVariable String id, @RequestBody final SocialNetworksData data) {

        final TalentSocialNetworksPersist.Command command = new TalentSocialNetworksPersist.Command(id, SocialNetworksData.toSocialNetworks(data));

        return Mono.fromFuture(commandBus.execute(command))
            .map(socialNetworks -> createdView((SocialNetworks) socialNetworks))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private ResponseEntity<String> createdView(final SocialNetworks socialNetworks) {
        metrics.incrementTalentSocialNetworksCreated();
        return new ResponseEntity<>(presenter.success(socialNetworks), HttpStatus.CREATED);
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentSocialNetworksError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentSocialNetworksNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }
}
