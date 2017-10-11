package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.skills.queries.SkillsTalentPaginated;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.skills.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.metrics.TalentApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.skills.middleware.SkillsQueryParams;
import io.bandit.limbo.limbo.application.api.resources.skills.presenters.SkillsOutputBoundary;
import io.bandit.limbo.limbo.modules.shared.model.*;
import io.bandit.limbo.limbo.modules.shared.middleware.PaginationUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Links;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class GetTalentSkillsResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_GET_MANY_SKILLS= "Gets the Skills for the current Talents.";

    private final IQueryBus queryBus;
    private final SkillsQueryParams queryParams;
    private final SkillsOutputBoundary presenter;
    private final TalentApiMetrics metrics;

    @Inject
    public GetTalentSkillsResource(final IQueryBus queryBus,
            final SkillsQueryParams queryParams,
            @Named("SkillsJsonPresenter") final SkillsOutputBoundary presenter,
            final TalentApiMetrics metrics) {

        this.queryParams = queryParams;
        this.presenter = presenter;
        this.queryBus = queryBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET_MANY_SKILLS, tags = {SWAGGER_DOC_TAG})
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
    @RequestMapping(value = TalentHttpRouter.TALENT_SKILLS_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = SkillsPaginatedResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SkillsUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SkillsForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = SkillsNotFound.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable String id) {

        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();

        final FieldsOptions fieldsOptions = queryParams.getFields();
        final FilterOptions filterOptions = queryParams.getFilters();
        final PageOptions pageOptions = queryParams.getPage();
        final SortOptions sortOptions = queryParams.getSort();

        return Mono.fromFuture(queryBus.dispatch(new SkillsTalentPaginated.Query(id, fieldsOptions, filterOptions, pageOptions, sortOptions)))
            .map(page -> paginatedView((Paginated<Skills>) page, request, pageOptions))
            .doOnError(this::errorView)
            .toFuture();
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

            metrics.incrementTalentSkillsSuccess();
            return ResponseEntity.ok().headers(headers).body(responseBody);

        } catch (Throwable e) {
            metrics.incrementTalentSkillsError();
            return new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentSkillsError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
