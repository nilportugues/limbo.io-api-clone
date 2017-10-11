package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.talent.commands.TalentPersist;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.talent.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.middleware.TalentQueryParams;
import io.bandit.limbo.limbo.application.api.resources.talent.presenters.TalentOutputBoundary;
import io.bandit.limbo.limbo.application.api.resources.talent.metrics.TalentApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.modules.shared.model.*;
import io.bandit.limbo.limbo.modules.shared.middleware.PaginationUtil;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Links;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
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

// _AggregateResource.java
@RestController
public class PutTalentResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_UPDATE = "Updates an existing talent.";

    private final TalentOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final TalentApiMetrics metrics;

    @Inject
    public PutTalentResource(
        final ICommandBus commandBus,
        @Named("TalentJsonPresenter") final TalentOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_UPDATE, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_ONE_ROUTE, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = TalentResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TalentUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TalentForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = TalentNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TalentInternalServerError.class)
    })
    public Future<ResponseEntity<String>> update(@PathVariable final String id,
                                                @RequestBody final TalentData data) {

        return Mono.fromFuture(commandBus.execute(new TalentPersist.Command(TalentData.toTalent(data))))
                .publishOn(Schedulers.parallel())
                .map(talent -> updatedView((Talent) talent))
                .doOnError(this::errorView)
                .defaultIfEmpty(notFoundView(id))
                .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> updatedView(final Talent talent) {
        metrics.incrementTalentUpdated();
        return new ResponseEntity<>(presenter.success(talent), HttpStatus.OK);
    }

}
