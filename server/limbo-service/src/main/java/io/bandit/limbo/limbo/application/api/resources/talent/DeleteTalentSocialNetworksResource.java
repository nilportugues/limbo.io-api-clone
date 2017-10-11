package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.talent.commands.TalentSocialNetworksDelete;
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
public class DeleteTalentSocialNetworksResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_DELETE_MANY_SOCIAL_NETWORKS = "Removes the talents for the current social networks.";

    private final SocialNetworksOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final TalentApiMetrics metrics;

    @Inject
    public DeleteTalentSocialNetworksResource(
        final ICommandBus commandBus,
        @Named("SocialNetworksJsonPresenter") final SocialNetworksOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_DELETE_MANY_SOCIAL_NETWORKS, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_SOCIALNETWORKS_ROUTE, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 401, message = "Unauthorized", response = SocialNetworksUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SocialNetworksForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = SocialNetworksNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = SocialNetworksInternalServerError.class)
    })
    public Future<ResponseEntity<String>> delete(@PathVariable String id) {

        return Mono.fromFuture(commandBus.execute(new TalentSocialNetworksDelete.Command(id)))
            .map(talent -> this.deleteView((Talent) talent))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }


    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentSocialNetworksError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentSocialNetworksNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> deleteView(final Talent talent) {
        metrics.incrementTalentSocialNetworksDeleted();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
