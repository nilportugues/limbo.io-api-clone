package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.talent.commands.TalentCompanyTraitsPersist;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.companytraits.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.companytraits.middleware.CompanyTraitsQueryParams;
import io.bandit.limbo.limbo.application.api.resources.companytraits.presenters.CompanyTraitsOutputBoundary;
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
public class PostTalentCompanyTraitsResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_POST_MANY_COMPANY_TRAITS = "Creates company traits for the current Talents.";

    private final CompanyTraitsQueryParams queryParams;
    private final CompanyTraitsOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final TalentApiMetrics metrics;

    @Inject
    public PostTalentCompanyTraitsResource(
        final ICommandBus commandBus,
        final CompanyTraitsQueryParams queryParams,
        @Named("CompanyTraitsJsonPresenter") final CompanyTraitsOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.queryParams = queryParams;
        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_POST_MANY_COMPANY_TRAITS, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_COMPANYTRAITS_ROUTE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Created", response = CompanyTraitsResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CompanyTraitsUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CompanyTraitsForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = CompanyTraitsNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = CompanyTraitsInternalServerError.class)
    })
    public Future<ResponseEntity<String>> create(@PathVariable String id, @RequestBody final CompanyTraitsData data) {

        final TalentCompanyTraitsPersist.Command command = new TalentCompanyTraitsPersist.Command(id, CompanyTraitsData.toCompanyTraits(data));

        return Mono.fromFuture(commandBus.execute(command))
            .map(companyTraits -> createdView((CompanyTraits) companyTraits))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private ResponseEntity<String> createdView(final CompanyTraits companyTraits) {
        metrics.incrementTalentCompanyTraitsCreated();
        return new ResponseEntity<>(presenter.success(companyTraits), HttpStatus.CREATED);
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentCompanyTraitsError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentCompanyTraitsNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }
}
