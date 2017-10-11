package io.bandit.limbo.limbo.application.api.resources.talentexperience;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.talentexperience.queries.TalentExperiencePaginated;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.application.api.resources.talentexperience.middleware.TalentExperienceQueryParams;
import io.bandit.limbo.limbo.application.api.resources.talentexperience.presenters.TalentExperienceJsonPresenter;
import io.bandit.limbo.limbo.application.api.resources.talentexperience.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talentexperience.metrics.TalentExperienceApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talentexperience.routing.TalentExperienceHttpRouter;
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
public class GetPaginatedTalentExperienceResource {

    private static final String SWAGGER_DOC_TAG = "Talent Experience";
    private static final String SWAGGER_DOC_GET_MANY = "Get all the talent experiences.";

    private final IQueryBus queryBus;
    private final TalentExperienceQueryParams queryParams;
    private final TalentExperienceJsonPresenter presenter;
    private final TalentExperienceApiMetrics metrics;

    @Inject
    public GetPaginatedTalentExperienceResource(final IQueryBus queryBus,
        final TalentExperienceQueryParams queryParams,
        final TalentExperienceJsonPresenter presenter,
        final TalentExperienceApiMetrics metrics) {

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

        //Filters for: years
        @ApiImplicitParam(name="filter[years]", dataType = "string", paramType = "query", value="**Years** matches this value."),
        @ApiImplicitParam(name="filter[not][years]", dataType = "string", paramType = "query", value="**Years** does not match this value."),
        @ApiImplicitParam(name="filter[ranges][years]", dataType = "string", paramType = "query", value="**Years** ranges between a min and a max. Separated by commas."),
        @ApiImplicitParam(name="filter[!ranges][years]", dataType = "string", paramType = "query", value="**Years** values that do not range between the min and max. Separated by commas."),
        @ApiImplicitParam(name="filter[in][years]", dataType = "string", paramType = "query", value="**Years** matches any of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[!in][years]", dataType = "string" , paramType = "query", value="**Years** matching none of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[gte][years]", dataType = "string", paramType = "query", value="**Years** is greater than or equals this value."),
        @ApiImplicitParam(name="filter[gt][years]", dataType = "string", paramType = "query", value="**Years** is greater than this value."),
        @ApiImplicitParam(name="filter[lte][years]", dataType = "string", paramType = "query", value="**Years** is less than or equals this value."),
        @ApiImplicitParam(name="filter[lt][years]", dataType = "string", paramType = "query", value="**Years** is less than this value."),
        @ApiImplicitParam(name="filter[has][years]", dataType = "string", paramType = "query", value="**Years** contains this value."),
        @ApiImplicitParam(name="filter[!has][years]", dataType = "string", paramType = "query", value="**Years** does not contain this value."),
        @ApiImplicitParam(name="filter[starts][years]", dataType = "string", paramType = "query", value="**Years** starts with this value."),
        @ApiImplicitParam(name="filter[ends][years]", dataType = "string", paramType = "query", value="**Years** does not start with this value."),
        @ApiImplicitParam(name="filter[!starts][years]", dataType = "string", paramType = "query", value="**Years** ends with this value."),
        @ApiImplicitParam(name="filter[!ends][years]", dataType = "string", paramType = "query", value="**Years** does not end with this value."),
    })
    @RequestMapping(value = TalentExperienceHttpRouter.TALENTEXPERIENCE_MANY_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = TalentExperiencePaginatedResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TalentExperienceUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TalentExperienceForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = TalentExperienceNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TalentExperienceInternalServerError.class)
    })
    public Future<ResponseEntity<String>> getAll() {
        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();

        final FieldsOptions fieldsOptions = queryParams.getFields();
        final FilterOptions filterOptions = queryParams.getFilters();
        final PageOptions pageOptions = queryParams.getPage();
        final SortOptions sortOptions = queryParams.getSort();

        return Mono.fromFuture(queryBus.dispatch(new TalentExperiencePaginated.Query(fieldsOptions, filterOptions, pageOptions, sortOptions)))
            .map(page -> paginatedView((Paginated<TalentExperience>) page, request, pageOptions))
            .doOnError(this::errorView)
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentExperienceError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> paginatedView(final Paginated<TalentExperience> page,
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

            metrics.incrementTalentExperienceSuccess();

            return ResponseEntity.ok().headers(headers).body(responseBody);

        } catch (Throwable e) {
            metrics.incrementTalentExperienceError();
            return new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
