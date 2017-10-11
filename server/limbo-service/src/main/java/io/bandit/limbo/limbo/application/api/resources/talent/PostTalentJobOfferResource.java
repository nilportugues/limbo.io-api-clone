package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.talent.commands.TalentJobOfferPersist;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.joboffer.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.joboffer.middleware.JobOfferQueryParams;
import io.bandit.limbo.limbo.application.api.resources.joboffer.presenters.JobOfferOutputBoundary;
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
public class PostTalentJobOfferResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_POST_MANY_JOB_OFFERS = "Creates job offer for the current Talents.";

    private final JobOfferQueryParams queryParams;
    private final JobOfferOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final TalentApiMetrics metrics;

    @Inject
    public PostTalentJobOfferResource(
        final ICommandBus commandBus,
        final JobOfferQueryParams queryParams,
        @Named("JobOfferJsonPresenter") final JobOfferOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.queryParams = queryParams;
        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_POST_MANY_JOB_OFFERS, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_JOBOFFER_ROUTE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Created", response = JobOfferResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = JobOfferUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = JobOfferForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = JobOfferNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = JobOfferInternalServerError.class)
    })
    public Future<ResponseEntity<String>> create(@PathVariable String id, @RequestBody final JobOfferData data) {

        final TalentJobOfferPersist.Command command = new TalentJobOfferPersist.Command(id, JobOfferData.toJobOffer(data));

        return Mono.fromFuture(commandBus.execute(command))
            .map(jobOffer -> createdView((JobOffer) jobOffer))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private ResponseEntity<String> createdView(final JobOffer jobOffer) {
        metrics.incrementTalentJobOfferCreated();
        return new ResponseEntity<>(presenter.success(jobOffer), HttpStatus.CREATED);
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentJobOfferError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentJobOfferNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }
}
