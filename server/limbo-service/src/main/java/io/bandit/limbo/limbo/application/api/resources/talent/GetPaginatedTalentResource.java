package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.talent.queries.TalentPaginated;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.talent.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.metrics.TalentApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talent.middleware.TalentQueryParams;
import io.bandit.limbo.limbo.application.api.resources.talent.presenters.TalentOutputBoundary;
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
public class GetPaginatedTalentResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_GET_MANY = "Get all the talents.";

    private final IQueryBus queryBus;
    private final TalentQueryParams queryParams;
    private final TalentOutputBoundary presenter;
    private final TalentApiMetrics metrics;

    @Inject
    public GetPaginatedTalentResource(final IQueryBus queryBus,
        final TalentQueryParams queryParams,
        @Named("TalentJsonPresenter") final TalentOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.queryBus = queryBus;
        this.queryParams = queryParams;
        this.presenter = presenter;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET_MANY, tags = {SWAGGER_DOC_TAG})
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page[number]", dataType = "int", paramType = "query", defaultValue = "1", value="Page number. Defaults to 1."),
        @ApiImplicitParam(name = "page[size]", dataType = "int", paramType = "query", defaultValue = "20", value="Page size. Defaults to 20."),
        @ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", value="Sorting by field name. Precede a field with a minus sign to sort descending. For instance: -property1,property2."),
        
        //Filters for: email
        @ApiImplicitParam(name="filter[email]", dataType = "string", paramType = "query", value="**Email** matches this value."),
        @ApiImplicitParam(name="filter[not][email]", dataType = "string", paramType = "query", value="**Email** does not match this value."),
        @ApiImplicitParam(name="filter[ranges][email]", dataType = "string", paramType = "query", value="**Email** ranges between a min and a max. Separated by commas."),
        @ApiImplicitParam(name="filter[!ranges][email]", dataType = "string", paramType = "query", value="**Email** values that do not range between the min and max. Separated by commas."),
        @ApiImplicitParam(name="filter[in][email]", dataType = "string", paramType = "query", value="**Email** matches any of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[!in][email]", dataType = "string" , paramType = "query", value="**Email** matching none of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[gte][email]", dataType = "string", paramType = "query", value="**Email** is greater than or equals this value."),
        @ApiImplicitParam(name="filter[gt][email]", dataType = "string", paramType = "query", value="**Email** is greater than this value."),
        @ApiImplicitParam(name="filter[lte][email]", dataType = "string", paramType = "query", value="**Email** is less than or equals this value."),
        @ApiImplicitParam(name="filter[lt][email]", dataType = "string", paramType = "query", value="**Email** is less than this value."),
        @ApiImplicitParam(name="filter[has][email]", dataType = "string", paramType = "query", value="**Email** contains this value."),
        @ApiImplicitParam(name="filter[!has][email]", dataType = "string", paramType = "query", value="**Email** does not contain this value."),
        @ApiImplicitParam(name="filter[starts][email]", dataType = "string", paramType = "query", value="**Email** starts with this value."),
        @ApiImplicitParam(name="filter[ends][email]", dataType = "string", paramType = "query", value="**Email** does not start with this value."),
        @ApiImplicitParam(name="filter[!starts][email]", dataType = "string", paramType = "query", value="**Email** ends with this value."),
        @ApiImplicitParam(name="filter[!ends][email]", dataType = "string", paramType = "query", value="**Email** does not end with this value."),

        //Filters for: password
        @ApiImplicitParam(name="filter[password]", dataType = "string", paramType = "query", value="**Password** matches this value."),
        @ApiImplicitParam(name="filter[not][password]", dataType = "string", paramType = "query", value="**Password** does not match this value."),
        @ApiImplicitParam(name="filter[ranges][password]", dataType = "string", paramType = "query", value="**Password** ranges between a min and a max. Separated by commas."),
        @ApiImplicitParam(name="filter[!ranges][password]", dataType = "string", paramType = "query", value="**Password** values that do not range between the min and max. Separated by commas."),
        @ApiImplicitParam(name="filter[in][password]", dataType = "string", paramType = "query", value="**Password** matches any of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[!in][password]", dataType = "string" , paramType = "query", value="**Password** matching none of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[gte][password]", dataType = "string", paramType = "query", value="**Password** is greater than or equals this value."),
        @ApiImplicitParam(name="filter[gt][password]", dataType = "string", paramType = "query", value="**Password** is greater than this value."),
        @ApiImplicitParam(name="filter[lte][password]", dataType = "string", paramType = "query", value="**Password** is less than or equals this value."),
        @ApiImplicitParam(name="filter[lt][password]", dataType = "string", paramType = "query", value="**Password** is less than this value."),
        @ApiImplicitParam(name="filter[has][password]", dataType = "string", paramType = "query", value="**Password** contains this value."),
        @ApiImplicitParam(name="filter[!has][password]", dataType = "string", paramType = "query", value="**Password** does not contain this value."),
        @ApiImplicitParam(name="filter[starts][password]", dataType = "string", paramType = "query", value="**Password** starts with this value."),
        @ApiImplicitParam(name="filter[ends][password]", dataType = "string", paramType = "query", value="**Password** does not start with this value."),
        @ApiImplicitParam(name="filter[!starts][password]", dataType = "string", paramType = "query", value="**Password** ends with this value."),
        @ApiImplicitParam(name="filter[!ends][password]", dataType = "string", paramType = "query", value="**Password** does not end with this value."),
    })
    @RequestMapping(value = TalentHttpRouter.TALENT_MANY_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = TalentPaginatedResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TalentUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TalentForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = TalentNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TalentInternalServerError.class)
    })
    public Future<ResponseEntity<String>> getAll() throws Throwable {
        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();

        final FieldsOptions fieldsOptions = queryParams.getFields();
        final FilterOptions filterOptions = queryParams.getFilters();
        final PageOptions pageOptions = queryParams.getPage();
        final SortOptions sortOptions = queryParams.getSort();

        return Mono.fromFuture(queryBus.dispatch(new TalentPaginated.Query(fieldsOptions, filterOptions, pageOptions, sortOptions)))
            .map(page -> paginatedView((Paginated<Talent>) page, request, pageOptions))
            .doOnError(this::errorView)
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> paginatedView(final Paginated<Talent> page,
                                          final HttpServletRequest request,
                                          final PageOptions pageOptions) {
        try {
            if (pageOptions.getNumber() > page.getTotalPages() && page.getTotalElements() > 0) {
                return new ResponseEntity<>(
                    presenter.outOfBoundResponse(pageOptions.getNumber(), pageOptions.getSize()),
                    HttpStatus.NOT_FOUND
                );
            }

            final String responseBody = presenter.paginatedResponse(
                    request.getQueryString(),
                    page);

            final Links links = new Links(
                    request.getRequestURL().toString(),
                    request.getQueryString(),
                    page);

            final HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                    links.getNext().getHref(),
                    links.getPrev().getHref(),
                    links.getLast().getHref(),
                    links.getFirst().getHref(),
                    page.getTotalElements());

            metrics.incrementTalentSuccess();

            return ResponseEntity.ok().headers(headers).body(responseBody);

        } catch (Throwable e) {
            metrics.incrementTalentError();
            return new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
