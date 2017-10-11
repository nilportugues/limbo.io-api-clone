package io.bandit.limbo.limbo.application.api.resources.talentprofile;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.talentprofile.queries.TalentProfilePaginated;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.application.api.resources.talentprofile.middleware.TalentProfileQueryParams;
import io.bandit.limbo.limbo.application.api.resources.talentprofile.presenters.TalentProfileJsonPresenter;
import io.bandit.limbo.limbo.application.api.resources.talentprofile.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talentprofile.metrics.TalentProfileApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talentprofile.routing.TalentProfileHttpRouter;
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
public class GetPaginatedTalentProfileResource {

    private static final String SWAGGER_DOC_TAG = "Talent Profile";
    private static final String SWAGGER_DOC_GET_MANY = "Get all the talent profiles.";

    private final IQueryBus queryBus;
    private final TalentProfileQueryParams queryParams;
    private final TalentProfileJsonPresenter presenter;
    private final TalentProfileApiMetrics metrics;

    @Inject
    public GetPaginatedTalentProfileResource(final IQueryBus queryBus,
        final TalentProfileQueryParams queryParams,
        final TalentProfileJsonPresenter presenter,
        final TalentProfileApiMetrics metrics) {

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

        //Filters for: firstName
        @ApiImplicitParam(name="filter[first-name]", dataType = "string", paramType = "query", value="**First Name** matches this value."),
        @ApiImplicitParam(name="filter[not][first-name]", dataType = "string", paramType = "query", value="**First Name** does not match this value."),
        @ApiImplicitParam(name="filter[ranges][first-name]", dataType = "string", paramType = "query", value="**First Name** ranges between a min and a max. Separated by commas."),
        @ApiImplicitParam(name="filter[!ranges][first-name]", dataType = "string", paramType = "query", value="**First Name** values that do not range between the min and max. Separated by commas."),
        @ApiImplicitParam(name="filter[in][first-name]", dataType = "string", paramType = "query", value="**First Name** matches any of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[!in][first-name]", dataType = "string" , paramType = "query", value="**First Name** matching none of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[gte][first-name]", dataType = "string", paramType = "query", value="**First Name** is greater than or equals this value."),
        @ApiImplicitParam(name="filter[gt][first-name]", dataType = "string", paramType = "query", value="**First Name** is greater than this value."),
        @ApiImplicitParam(name="filter[lte][first-name]", dataType = "string", paramType = "query", value="**First Name** is less than or equals this value."),
        @ApiImplicitParam(name="filter[lt][first-name]", dataType = "string", paramType = "query", value="**First Name** is less than this value."),
        @ApiImplicitParam(name="filter[has][first-name]", dataType = "string", paramType = "query", value="**First Name** contains this value."),
        @ApiImplicitParam(name="filter[!has][first-name]", dataType = "string", paramType = "query", value="**First Name** does not contain this value."),
        @ApiImplicitParam(name="filter[starts][first-name]", dataType = "string", paramType = "query", value="**First Name** starts with this value."),
        @ApiImplicitParam(name="filter[ends][first-name]", dataType = "string", paramType = "query", value="**First Name** does not start with this value."),
        @ApiImplicitParam(name="filter[!starts][first-name]", dataType = "string", paramType = "query", value="**First Name** ends with this value."),
        @ApiImplicitParam(name="filter[!ends][first-name]", dataType = "string", paramType = "query", value="**First Name** does not end with this value."),

        //Filters for: lastName
        @ApiImplicitParam(name="filter[last-name]", dataType = "string", paramType = "query", value="**Last Name** matches this value."),
        @ApiImplicitParam(name="filter[not][last-name]", dataType = "string", paramType = "query", value="**Last Name** does not match this value."),
        @ApiImplicitParam(name="filter[ranges][last-name]", dataType = "string", paramType = "query", value="**Last Name** ranges between a min and a max. Separated by commas."),
        @ApiImplicitParam(name="filter[!ranges][last-name]", dataType = "string", paramType = "query", value="**Last Name** values that do not range between the min and max. Separated by commas."),
        @ApiImplicitParam(name="filter[in][last-name]", dataType = "string", paramType = "query", value="**Last Name** matches any of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[!in][last-name]", dataType = "string" , paramType = "query", value="**Last Name** matching none of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[gte][last-name]", dataType = "string", paramType = "query", value="**Last Name** is greater than or equals this value."),
        @ApiImplicitParam(name="filter[gt][last-name]", dataType = "string", paramType = "query", value="**Last Name** is greater than this value."),
        @ApiImplicitParam(name="filter[lte][last-name]", dataType = "string", paramType = "query", value="**Last Name** is less than or equals this value."),
        @ApiImplicitParam(name="filter[lt][last-name]", dataType = "string", paramType = "query", value="**Last Name** is less than this value."),
        @ApiImplicitParam(name="filter[has][last-name]", dataType = "string", paramType = "query", value="**Last Name** contains this value."),
        @ApiImplicitParam(name="filter[!has][last-name]", dataType = "string", paramType = "query", value="**Last Name** does not contain this value."),
        @ApiImplicitParam(name="filter[starts][last-name]", dataType = "string", paramType = "query", value="**Last Name** starts with this value."),
        @ApiImplicitParam(name="filter[ends][last-name]", dataType = "string", paramType = "query", value="**Last Name** does not start with this value."),
        @ApiImplicitParam(name="filter[!starts][last-name]", dataType = "string", paramType = "query", value="**Last Name** ends with this value."),
        @ApiImplicitParam(name="filter[!ends][last-name]", dataType = "string", paramType = "query", value="**Last Name** does not end with this value."),
    })
    @RequestMapping(value = TalentProfileHttpRouter.TALENTPROFILE_MANY_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = TalentProfilePaginatedResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TalentProfileUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TalentProfileForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = TalentProfileNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TalentProfileInternalServerError.class)
    })
    public Future<ResponseEntity<String>> getAll() {
        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();

        final FieldsOptions fieldsOptions = queryParams.getFields();
        final FilterOptions filterOptions = queryParams.getFilters();
        final PageOptions pageOptions = queryParams.getPage();
        final SortOptions sortOptions = queryParams.getSort();

        return Mono.fromFuture(queryBus.dispatch(new TalentProfilePaginated.Query(fieldsOptions, filterOptions, pageOptions, sortOptions)))
            .map(page -> paginatedView((Paginated<TalentProfile>) page, request, pageOptions))
            .doOnError(this::errorView)
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentProfileError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> paginatedView(final Paginated<TalentProfile> page,
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

            metrics.incrementTalentProfileSuccess();

            return ResponseEntity.ok().headers(headers).body(responseBody);

        } catch (Throwable e) {
            metrics.incrementTalentProfileError();
            return new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
