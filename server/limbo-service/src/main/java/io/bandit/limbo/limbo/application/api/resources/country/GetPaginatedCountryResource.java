package io.bandit.limbo.limbo.application.api.resources.country;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.country.queries.CountryPaginated;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.application.api.resources.country.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.country.metrics.CountryApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.country.middleware.CountryQueryParams;
import io.bandit.limbo.limbo.application.api.resources.country.presenters.CountryOutputBoundary;
import io.bandit.limbo.limbo.application.api.resources.country.routing.CountryHttpRouter;
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
public class GetPaginatedCountryResource {

    private static final String SWAGGER_DOC_TAG = "Country";
    private static final String SWAGGER_DOC_GET_MANY = "Get all the countries.";

    private final IQueryBus queryBus;
    private final CountryQueryParams queryParams;
    private final CountryOutputBoundary presenter;
    private final CountryApiMetrics metrics;

    @Inject
    public GetPaginatedCountryResource(final IQueryBus queryBus,
        final CountryQueryParams queryParams,
        @Named("CountryJsonPresenter") final CountryOutputBoundary presenter,
        final CountryApiMetrics metrics) {

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
        
        //Filters for: name
        @ApiImplicitParam(name="filter[name]", dataType = "string", paramType = "query", value="**Name** matches this value."),
        @ApiImplicitParam(name="filter[not][name]", dataType = "string", paramType = "query", value="**Name** does not match this value."),
        @ApiImplicitParam(name="filter[ranges][name]", dataType = "string", paramType = "query", value="**Name** ranges between a min and a max. Separated by commas."),
        @ApiImplicitParam(name="filter[!ranges][name]", dataType = "string", paramType = "query", value="**Name** values that do not range between the min and max. Separated by commas."),
        @ApiImplicitParam(name="filter[in][name]", dataType = "string", paramType = "query", value="**Name** matches any of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[!in][name]", dataType = "string" , paramType = "query", value="**Name** matching none of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[gte][name]", dataType = "string", paramType = "query", value="**Name** is greater than or equals this value."),
        @ApiImplicitParam(name="filter[gt][name]", dataType = "string", paramType = "query", value="**Name** is greater than this value."),
        @ApiImplicitParam(name="filter[lte][name]", dataType = "string", paramType = "query", value="**Name** is less than or equals this value."),
        @ApiImplicitParam(name="filter[lt][name]", dataType = "string", paramType = "query", value="**Name** is less than this value."),
        @ApiImplicitParam(name="filter[has][name]", dataType = "string", paramType = "query", value="**Name** contains this value."),
        @ApiImplicitParam(name="filter[!has][name]", dataType = "string", paramType = "query", value="**Name** does not contain this value."),
        @ApiImplicitParam(name="filter[starts][name]", dataType = "string", paramType = "query", value="**Name** starts with this value."),
        @ApiImplicitParam(name="filter[ends][name]", dataType = "string", paramType = "query", value="**Name** does not start with this value."),
        @ApiImplicitParam(name="filter[!starts][name]", dataType = "string", paramType = "query", value="**Name** ends with this value."),
        @ApiImplicitParam(name="filter[!ends][name]", dataType = "string", paramType = "query", value="**Name** does not end with this value."),

        //Filters for: countryCode
        @ApiImplicitParam(name="filter[country-code]", dataType = "string", paramType = "query", value="**Country Code** matches this value."),
        @ApiImplicitParam(name="filter[not][country-code]", dataType = "string", paramType = "query", value="**Country Code** does not match this value."),
        @ApiImplicitParam(name="filter[ranges][country-code]", dataType = "string", paramType = "query", value="**Country Code** ranges between a min and a max. Separated by commas."),
        @ApiImplicitParam(name="filter[!ranges][country-code]", dataType = "string", paramType = "query", value="**Country Code** values that do not range between the min and max. Separated by commas."),
        @ApiImplicitParam(name="filter[in][country-code]", dataType = "string", paramType = "query", value="**Country Code** matches any of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[!in][country-code]", dataType = "string" , paramType = "query", value="**Country Code** matching none of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[gte][country-code]", dataType = "string", paramType = "query", value="**Country Code** is greater than or equals this value."),
        @ApiImplicitParam(name="filter[gt][country-code]", dataType = "string", paramType = "query", value="**Country Code** is greater than this value."),
        @ApiImplicitParam(name="filter[lte][country-code]", dataType = "string", paramType = "query", value="**Country Code** is less than or equals this value."),
        @ApiImplicitParam(name="filter[lt][country-code]", dataType = "string", paramType = "query", value="**Country Code** is less than this value."),
        @ApiImplicitParam(name="filter[has][country-code]", dataType = "string", paramType = "query", value="**Country Code** contains this value."),
        @ApiImplicitParam(name="filter[!has][country-code]", dataType = "string", paramType = "query", value="**Country Code** does not contain this value."),
        @ApiImplicitParam(name="filter[starts][country-code]", dataType = "string", paramType = "query", value="**Country Code** starts with this value."),
        @ApiImplicitParam(name="filter[ends][country-code]", dataType = "string", paramType = "query", value="**Country Code** does not start with this value."),
        @ApiImplicitParam(name="filter[!starts][country-code]", dataType = "string", paramType = "query", value="**Country Code** ends with this value."),
        @ApiImplicitParam(name="filter[!ends][country-code]", dataType = "string", paramType = "query", value="**Country Code** does not end with this value."),
    })
    @RequestMapping(value = CountryHttpRouter.COUNTRY_MANY_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = CountryPaginatedResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CountryUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CountryForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = CountryNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = CountryInternalServerError.class)
    })
    public Future<ResponseEntity<String>> getAll() throws Throwable {
        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();

        final FieldsOptions fieldsOptions = queryParams.getFields();
        final FilterOptions filterOptions = queryParams.getFilters();
        final PageOptions pageOptions = queryParams.getPage();
        final SortOptions sortOptions = queryParams.getSort();

        return Mono.fromFuture(queryBus.dispatch(new CountryPaginated.Query(fieldsOptions, filterOptions, pageOptions, sortOptions)))
            .map(page -> paginatedView((Paginated<Country>) page, request, pageOptions))
            .doOnError(this::errorView)
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementCountryError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> paginatedView(final Paginated<Country> page,
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

            metrics.incrementCountrySuccess();

            return ResponseEntity.ok().headers(headers).body(responseBody);

        } catch (Throwable e) {
            metrics.incrementCountryError();
            return new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
