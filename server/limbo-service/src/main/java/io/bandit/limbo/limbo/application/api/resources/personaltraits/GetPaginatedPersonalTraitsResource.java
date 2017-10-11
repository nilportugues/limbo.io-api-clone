package io.bandit.limbo.limbo.application.api.resources.personaltraits;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.personaltraits.queries.PersonalTraitsPaginated;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.application.api.resources.personaltraits.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.personaltraits.metrics.PersonalTraitsApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.personaltraits.middleware.PersonalTraitsQueryParams;
import io.bandit.limbo.limbo.application.api.resources.personaltraits.presenters.PersonalTraitsOutputBoundary;
import io.bandit.limbo.limbo.application.api.resources.personaltraits.routing.PersonalTraitsHttpRouter;
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
public class GetPaginatedPersonalTraitsResource {

    private static final String SWAGGER_DOC_TAG = "Personal Traits";
    private static final String SWAGGER_DOC_GET_MANY = "Get all the personal traits.";

    private final IQueryBus queryBus;
    private final PersonalTraitsQueryParams queryParams;
    private final PersonalTraitsOutputBoundary presenter;
    private final PersonalTraitsApiMetrics metrics;

    @Inject
    public GetPaginatedPersonalTraitsResource(final IQueryBus queryBus,
        final PersonalTraitsQueryParams queryParams,
        @Named("PersonalTraitsJsonPresenter") final PersonalTraitsOutputBoundary presenter,
        final PersonalTraitsApiMetrics metrics) {

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
        
        //Filters for: description
        @ApiImplicitParam(name="filter[description]", dataType = "string", paramType = "query", value="**Description** matches this value."),
        @ApiImplicitParam(name="filter[not][description]", dataType = "string", paramType = "query", value="**Description** does not match this value."),
        @ApiImplicitParam(name="filter[ranges][description]", dataType = "string", paramType = "query", value="**Description** ranges between a min and a max. Separated by commas."),
        @ApiImplicitParam(name="filter[!ranges][description]", dataType = "string", paramType = "query", value="**Description** values that do not range between the min and max. Separated by commas."),
        @ApiImplicitParam(name="filter[in][description]", dataType = "string", paramType = "query", value="**Description** matches any of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[!in][description]", dataType = "string" , paramType = "query", value="**Description** matching none of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[gte][description]", dataType = "string", paramType = "query", value="**Description** is greater than or equals this value."),
        @ApiImplicitParam(name="filter[gt][description]", dataType = "string", paramType = "query", value="**Description** is greater than this value."),
        @ApiImplicitParam(name="filter[lte][description]", dataType = "string", paramType = "query", value="**Description** is less than or equals this value."),
        @ApiImplicitParam(name="filter[lt][description]", dataType = "string", paramType = "query", value="**Description** is less than this value."),
        @ApiImplicitParam(name="filter[has][description]", dataType = "string", paramType = "query", value="**Description** contains this value."),
        @ApiImplicitParam(name="filter[!has][description]", dataType = "string", paramType = "query", value="**Description** does not contain this value."),
        @ApiImplicitParam(name="filter[starts][description]", dataType = "string", paramType = "query", value="**Description** starts with this value."),
        @ApiImplicitParam(name="filter[ends][description]", dataType = "string", paramType = "query", value="**Description** does not start with this value."),
        @ApiImplicitParam(name="filter[!starts][description]", dataType = "string", paramType = "query", value="**Description** ends with this value."),
        @ApiImplicitParam(name="filter[!ends][description]", dataType = "string", paramType = "query", value="**Description** does not end with this value."),
    })
    @RequestMapping(value = PersonalTraitsHttpRouter.PERSONALTRAITS_MANY_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = PersonalTraitsPaginatedResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = PersonalTraitsUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = PersonalTraitsForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = PersonalTraitsNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = PersonalTraitsInternalServerError.class)
    })
    public Future<ResponseEntity<String>> getAll() throws Throwable {
        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();

        final FieldsOptions fieldsOptions = queryParams.getFields();
        final FilterOptions filterOptions = queryParams.getFilters();
        final PageOptions pageOptions = queryParams.getPage();
        final SortOptions sortOptions = queryParams.getSort();

        return Mono.fromFuture(queryBus.dispatch(new PersonalTraitsPaginated.Query(fieldsOptions, filterOptions, pageOptions, sortOptions)))
            .map(page -> paginatedView((Paginated<PersonalTraits>) page, request, pageOptions))
            .doOnError(this::errorView)
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementPersonalTraitsError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> paginatedView(final Paginated<PersonalTraits> page,
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

            metrics.incrementPersonalTraitsSuccess();

            return ResponseEntity.ok().headers(headers).body(responseBody);

        } catch (Throwable e) {
            metrics.incrementPersonalTraitsError();
            return new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
