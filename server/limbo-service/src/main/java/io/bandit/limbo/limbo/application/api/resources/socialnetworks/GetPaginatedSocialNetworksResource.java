package io.bandit.limbo.limbo.application.api.resources.socialnetworks;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.socialnetworks.queries.SocialNetworksPaginated;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.application.api.resources.socialnetworks.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.socialnetworks.metrics.SocialNetworksApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.socialnetworks.middleware.SocialNetworksQueryParams;
import io.bandit.limbo.limbo.application.api.resources.socialnetworks.presenters.SocialNetworksOutputBoundary;
import io.bandit.limbo.limbo.application.api.resources.socialnetworks.routing.SocialNetworksHttpRouter;
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
public class GetPaginatedSocialNetworksResource {

    private static final String SWAGGER_DOC_TAG = "Social Networks";
    private static final String SWAGGER_DOC_GET_MANY = "Get all the social networks.";

    private final IQueryBus queryBus;
    private final SocialNetworksQueryParams queryParams;
    private final SocialNetworksOutputBoundary presenter;
    private final SocialNetworksApiMetrics metrics;

    @Inject
    public GetPaginatedSocialNetworksResource(final IQueryBus queryBus,
        final SocialNetworksQueryParams queryParams,
        @Named("SocialNetworksJsonPresenter") final SocialNetworksOutputBoundary presenter,
        final SocialNetworksApiMetrics metrics) {

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

        //Filters for: url
        @ApiImplicitParam(name="filter[url]", dataType = "string", paramType = "query", value="**Url** matches this value."),
        @ApiImplicitParam(name="filter[not][url]", dataType = "string", paramType = "query", value="**Url** does not match this value."),
        @ApiImplicitParam(name="filter[ranges][url]", dataType = "string", paramType = "query", value="**Url** ranges between a min and a max. Separated by commas."),
        @ApiImplicitParam(name="filter[!ranges][url]", dataType = "string", paramType = "query", value="**Url** values that do not range between the min and max. Separated by commas."),
        @ApiImplicitParam(name="filter[in][url]", dataType = "string", paramType = "query", value="**Url** matches any of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[!in][url]", dataType = "string" , paramType = "query", value="**Url** matching none of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[gte][url]", dataType = "string", paramType = "query", value="**Url** is greater than or equals this value."),
        @ApiImplicitParam(name="filter[gt][url]", dataType = "string", paramType = "query", value="**Url** is greater than this value."),
        @ApiImplicitParam(name="filter[lte][url]", dataType = "string", paramType = "query", value="**Url** is less than or equals this value."),
        @ApiImplicitParam(name="filter[lt][url]", dataType = "string", paramType = "query", value="**Url** is less than this value."),
        @ApiImplicitParam(name="filter[has][url]", dataType = "string", paramType = "query", value="**Url** contains this value."),
        @ApiImplicitParam(name="filter[!has][url]", dataType = "string", paramType = "query", value="**Url** does not contain this value."),
        @ApiImplicitParam(name="filter[starts][url]", dataType = "string", paramType = "query", value="**Url** starts with this value."),
        @ApiImplicitParam(name="filter[ends][url]", dataType = "string", paramType = "query", value="**Url** does not start with this value."),
        @ApiImplicitParam(name="filter[!starts][url]", dataType = "string", paramType = "query", value="**Url** ends with this value."),
        @ApiImplicitParam(name="filter[!ends][url]", dataType = "string", paramType = "query", value="**Url** does not end with this value."),
    })
    @RequestMapping(value = SocialNetworksHttpRouter.SOCIALNETWORKS_MANY_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = SocialNetworksPaginatedResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SocialNetworksUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SocialNetworksForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = SocialNetworksNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = SocialNetworksInternalServerError.class)
    })
    public Future<ResponseEntity<String>> getAll() throws Throwable {
        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();

        final FieldsOptions fieldsOptions = queryParams.getFields();
        final FilterOptions filterOptions = queryParams.getFilters();
        final PageOptions pageOptions = queryParams.getPage();
        final SortOptions sortOptions = queryParams.getSort();

        return Mono.fromFuture(queryBus.dispatch(new SocialNetworksPaginated.Query(fieldsOptions, filterOptions, pageOptions, sortOptions)))
            .map(page -> paginatedView((Paginated<SocialNetworks>) page, request, pageOptions))
            .doOnError(this::errorView)
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementSocialNetworksError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> paginatedView(final Paginated<SocialNetworks> page,
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

            metrics.incrementSocialNetworksSuccess();

            return ResponseEntity.ok().headers(headers).body(responseBody);

        } catch (Throwable e) {
            metrics.incrementSocialNetworksError();
            return new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
