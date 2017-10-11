package io.bandit.limbo.limbo.application.api.resources.talentrole;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.talentrole.queries.TalentRolePaginated;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.application.api.resources.talentrole.middleware.TalentRoleQueryParams;
import io.bandit.limbo.limbo.application.api.resources.talentrole.presenters.TalentRoleJsonPresenter;
import io.bandit.limbo.limbo.application.api.resources.talentrole.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talentrole.metrics.TalentRoleApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talentrole.routing.TalentRoleHttpRouter;
import io.bandit.limbo.limbo.modules.shared.model.*;
import io.bandit.limbo.limbo.modules.shared.middleware.PaginationUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Links;
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

// _EntityResource.java
@RestController
public class GetPaginatedTalentRoleResource {

    private static final String SWAGGER_DOC_TAG = "Talent Role";
    private static final String SWAGGER_DOC_GET_MANY = "Get all the talent roles.";

    private final IQueryBus queryBus;
    private final TalentRoleQueryParams queryParams;
    private final TalentRoleJsonPresenter presenter;
    private final TalentRoleApiMetrics metrics;

    @Inject
    public GetPaginatedTalentRoleResource(final IQueryBus queryBus,
        final TalentRoleQueryParams queryParams,
        final TalentRoleJsonPresenter presenter,
        final TalentRoleApiMetrics metrics) {

        this.queryBus = queryBus;
        this.presenter = presenter;
        this.queryParams = queryParams;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET_MANY, tags = {SWAGGER_DOC_TAG})
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page[number]", dataType = "int", paramType = "query", defaultValue = "1", value="Page number. Defaults to 1."),
        @ApiImplicitParam(name = "page[size]", dataType = "int", paramType = "query", defaultValue = "20", value="Page size. Defaults to 20."),
        @ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", value="Sorting by field name. Precede a field with a minus sign to sort descending. For instance: -property1,property2."),

        //Filters for: title
        @ApiImplicitParam(name="filter[title]", dataType = "string", paramType = "query", value="**Title** matches this value."),
        @ApiImplicitParam(name="filter[not][title]", dataType = "string", paramType = "query", value="**Title** does not match this value."),
        @ApiImplicitParam(name="filter[ranges][title]", dataType = "string", paramType = "query", value="**Title** ranges between a min and a max. Separated by commas."),
        @ApiImplicitParam(name="filter[!ranges][title]", dataType = "string", paramType = "query", value="**Title** values that do not range between the min and max. Separated by commas."),
        @ApiImplicitParam(name="filter[in][title]", dataType = "string", paramType = "query", value="**Title** matches any of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[!in][title]", dataType = "string" , paramType = "query", value="**Title** matching none of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[gte][title]", dataType = "string", paramType = "query", value="**Title** is greater than or equals this value."),
        @ApiImplicitParam(name="filter[gt][title]", dataType = "string", paramType = "query", value="**Title** is greater than this value."),
        @ApiImplicitParam(name="filter[lte][title]", dataType = "string", paramType = "query", value="**Title** is less than or equals this value."),
        @ApiImplicitParam(name="filter[lt][title]", dataType = "string", paramType = "query", value="**Title** is less than this value."),
        @ApiImplicitParam(name="filter[has][title]", dataType = "string", paramType = "query", value="**Title** contains this value."),
        @ApiImplicitParam(name="filter[!has][title]", dataType = "string", paramType = "query", value="**Title** does not contain this value."),
        @ApiImplicitParam(name="filter[starts][title]", dataType = "string", paramType = "query", value="**Title** starts with this value."),
        @ApiImplicitParam(name="filter[ends][title]", dataType = "string", paramType = "query", value="**Title** does not start with this value."),
        @ApiImplicitParam(name="filter[!starts][title]", dataType = "string", paramType = "query", value="**Title** ends with this value."),
        @ApiImplicitParam(name="filter[!ends][title]", dataType = "string", paramType = "query", value="**Title** does not end with this value."),

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
    @RequestMapping(value = TalentRoleHttpRouter.TALENTROLE_MANY_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = TalentRolePaginatedResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TalentRoleUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TalentRoleForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = TalentRoleNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TalentRoleInternalServerError.class)
    })
    public Future<ResponseEntity<String>> getAll() {
        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();

        final FieldsOptions fieldsOptions = queryParams.getFields();
        final FilterOptions filterOptions = queryParams.getFilters();
        final PageOptions pageOptions = queryParams.getPage();
        final SortOptions sortOptions = queryParams.getSort();

        return Mono.fromFuture(queryBus.dispatch(new TalentRolePaginated.Query(fieldsOptions, filterOptions, pageOptions, sortOptions)))
            .map(page -> paginatedView((Paginated<TalentRole>) page, request, pageOptions))
            .doOnError(this::errorView)
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentRoleError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> paginatedView(final Paginated<TalentRole> page,
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

            metrics.incrementTalentRoleSuccess();

            return ResponseEntity.ok().headers(headers).body(responseBody);

        } catch (Throwable e) {
            metrics.incrementTalentRoleError();
            return new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
