package io.bandit.limbo.limbo.application.api.resources.joboffer;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.joboffer.queries.GetJobOffer;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.application.api.resources.joboffer.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.joboffer.metrics.JobOfferApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.joboffer.middleware.JobOfferQueryParams;
import io.bandit.limbo.limbo.application.api.resources.joboffer.presenters.JobOfferOutputBoundary;
import io.bandit.limbo.limbo.application.api.resources.joboffer.routing.JobOfferHttpRouter;
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
public class GetJobOfferResource {

    private static final String SWAGGER_DOC_TAG = "Job Offer";
    private static final String SWAGGER_DOC_GET = "Get a job offer by ID.";

    private final IQueryBus queryBus;
    private final JobOfferOutputBoundary presenter;
    private final JobOfferApiMetrics metrics;

    @Inject
    public GetJobOfferResource(final IQueryBus queryBus,
            @Named("JobOfferJsonPresenter") final JobOfferOutputBoundary presenter,
            final JobOfferApiMetrics metrics) {

        this.presenter = presenter;
        this.queryBus = queryBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = JobOfferHttpRouter.JOBOFFER_ONE_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = JobOfferResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = JobOfferUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = JobOfferForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = JobOfferNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = JobOfferInternalServerError.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable final String id) {

        return Mono.fromFuture(queryBus.dispatch(new GetJobOffer.Query(id)))
            .map(jobOffer -> this.okView((JobOffer) jobOffer))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementJobOfferError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> okView(final JobOffer jobOffer) {
        metrics.incrementJobOfferSuccess();
        return new ResponseEntity<>(presenter.success(jobOffer), HttpStatus.OK);
    }

    private ResponseEntity<String> notFoundView(final String jobOfferId) {
        metrics.incrementJobOfferNotFound();
        return new ResponseEntity<>(presenter.notFound(jobOfferId), HttpStatus.NOT_FOUND);
    }
}
