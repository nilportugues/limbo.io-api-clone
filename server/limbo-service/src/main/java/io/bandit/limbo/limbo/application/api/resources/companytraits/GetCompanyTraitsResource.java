package io.bandit.limbo.limbo.application.api.resources.companytraits;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.companytraits.queries.GetCompanyTraits;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.application.api.resources.companytraits.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.companytraits.metrics.CompanyTraitsApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.companytraits.middleware.CompanyTraitsQueryParams;
import io.bandit.limbo.limbo.application.api.resources.companytraits.presenters.CompanyTraitsOutputBoundary;
import io.bandit.limbo.limbo.application.api.resources.companytraits.routing.CompanyTraitsHttpRouter;
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
public class GetCompanyTraitsResource {

    private static final String SWAGGER_DOC_TAG = "Company Traits";
    private static final String SWAGGER_DOC_GET = "Get a company traits by ID.";

    private final IQueryBus queryBus;
    private final CompanyTraitsOutputBoundary presenter;
    private final CompanyTraitsApiMetrics metrics;

    @Inject
    public GetCompanyTraitsResource(final IQueryBus queryBus,
            @Named("CompanyTraitsJsonPresenter") final CompanyTraitsOutputBoundary presenter,
            final CompanyTraitsApiMetrics metrics) {

        this.presenter = presenter;
        this.queryBus = queryBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = CompanyTraitsHttpRouter.COMPANYTRAITS_ONE_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = CompanyTraitsResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CompanyTraitsUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CompanyTraitsForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = CompanyTraitsNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = CompanyTraitsInternalServerError.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable final String id) {

        return Mono.fromFuture(queryBus.dispatch(new GetCompanyTraits.Query(id)))
            .map(companyTraits -> this.okView((CompanyTraits) companyTraits))
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementCompanyTraitsError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> okView(final CompanyTraits companyTraits) {
        metrics.incrementCompanyTraitsSuccess();
        return new ResponseEntity<>(presenter.success(companyTraits), HttpStatus.OK);
    }

    private ResponseEntity<String> notFoundView(final String companyTraitsId) {
        metrics.incrementCompanyTraitsNotFound();
        return new ResponseEntity<>(presenter.notFound(companyTraitsId), HttpStatus.NOT_FOUND);
    }
}
