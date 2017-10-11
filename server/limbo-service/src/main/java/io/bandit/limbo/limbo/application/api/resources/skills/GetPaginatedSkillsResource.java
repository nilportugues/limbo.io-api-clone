package io.bandit.limbo.limbo.application.api.resources.skills;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.skills.queries.SkillsPaginated;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.application.api.resources.skills.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.skills.metrics.SkillsApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.skills.middleware.SkillsQueryParams;
import io.bandit.limbo.limbo.application.api.resources.skills.presenters.SkillsOutputBoundary;
import io.bandit.limbo.limbo.application.api.resources.skills.routing.SkillsHttpRouter;
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
public class GetPaginatedSkillsResource {

    private static final String SWAGGER_DOC_TAG = "Skills";
    private static final String SWAGGER_DOC_GET_MANY = "Get all the skills.";

    private final IQueryBus queryBus;
    private final SkillsQueryParams queryParams;
    private final SkillsOutputBoundary presenter;
    private final SkillsApiMetrics metrics;

    @Inject
    public GetPaginatedSkillsResource(final IQueryBus queryBus,
        final SkillsQueryParams queryParams,
        @Named("SkillsJsonPresenter") final SkillsOutputBoundary presenter,
        final SkillsApiMetrics metrics) {

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
        
        //Filters for: skill
        @ApiImplicitParam(name="filter[skill]", dataType = "string", paramType = "query", value="**Skill** matches this value."),
        @ApiImplicitParam(name="filter[not][skill]", dataType = "string", paramType = "query", value="**Skill** does not match this value."),
        @ApiImplicitParam(name="filter[ranges][skill]", dataType = "string", paramType = "query", value="**Skill** ranges between a min and a max. Separated by commas."),
        @ApiImplicitParam(name="filter[!ranges][skill]", dataType = "string", paramType = "query", value="**Skill** values that do not range between the min and max. Separated by commas."),
        @ApiImplicitParam(name="filter[in][skill]", dataType = "string", paramType = "query", value="**Skill** matches any of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[!in][skill]", dataType = "string" , paramType = "query", value="**Skill** matching none of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[gte][skill]", dataType = "string", paramType = "query", value="**Skill** is greater than or equals this value."),
        @ApiImplicitParam(name="filter[gt][skill]", dataType = "string", paramType = "query", value="**Skill** is greater than this value."),
        @ApiImplicitParam(name="filter[lte][skill]", dataType = "string", paramType = "query", value="**Skill** is less than or equals this value."),
        @ApiImplicitParam(name="filter[lt][skill]", dataType = "string", paramType = "query", value="**Skill** is less than this value."),
        @ApiImplicitParam(name="filter[has][skill]", dataType = "string", paramType = "query", value="**Skill** contains this value."),
        @ApiImplicitParam(name="filter[!has][skill]", dataType = "string", paramType = "query", value="**Skill** does not contain this value."),
        @ApiImplicitParam(name="filter[starts][skill]", dataType = "string", paramType = "query", value="**Skill** starts with this value."),
        @ApiImplicitParam(name="filter[ends][skill]", dataType = "string", paramType = "query", value="**Skill** does not start with this value."),
        @ApiImplicitParam(name="filter[!starts][skill]", dataType = "string", paramType = "query", value="**Skill** ends with this value."),
        @ApiImplicitParam(name="filter[!ends][skill]", dataType = "string", paramType = "query", value="**Skill** does not end with this value."),
    })
    @RequestMapping(value = SkillsHttpRouter.SKILLS_MANY_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = SkillsPaginatedResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SkillsUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SkillsForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = SkillsNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = SkillsInternalServerError.class)
    })
    public Future<ResponseEntity<String>> getAll() throws Throwable {
        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();

        final FieldsOptions fieldsOptions = queryParams.getFields();
        final FilterOptions filterOptions = queryParams.getFilters();
        final PageOptions pageOptions = queryParams.getPage();
        final SortOptions sortOptions = queryParams.getSort();

        return Mono.fromFuture(queryBus.dispatch(new SkillsPaginated.Query(fieldsOptions, filterOptions, pageOptions, sortOptions)))
            .map(page -> paginatedView((Paginated<Skills>) page, request, pageOptions))
            .doOnError(this::errorView)
            .toFuture();
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementSkillsError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> paginatedView(final Paginated<Skills> page,
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

            metrics.incrementSkillsSuccess();

            return ResponseEntity.ok().headers(headers).body(responseBody);

        } catch (Throwable e) {
            metrics.incrementSkillsError();
            return new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
