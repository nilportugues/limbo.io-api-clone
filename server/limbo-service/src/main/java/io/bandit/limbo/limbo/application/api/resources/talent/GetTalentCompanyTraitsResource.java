package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.companytraits.queries.CompanyTraitsTalentPaginated;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.companytraits.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.metrics.TalentApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.companytraits.middleware.CompanyTraitsQueryParams;
import io.bandit.limbo.limbo.application.api.resources.companytraits.presenters.CompanyTraitsOutputBoundary;
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
public class GetTalentCompanyTraitsResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_GET_MANY_COMPANY_TRAITS= "Gets the CompanyTraits for the current Talents.";

    private final IQueryBus queryBus;
    private final CompanyTraitsQueryParams queryParams;
    private final CompanyTraitsOutputBoundary presenter;
    private final TalentApiMetrics metrics;

    @Inject
    public GetTalentCompanyTraitsResource(final IQueryBus queryBus,
            final CompanyTraitsQueryParams queryParams,
            @Named("CompanyTraitsJsonPresenter") final CompanyTraitsOutputBoundary presenter,
            final TalentApiMetrics metrics) {

        this.queryParams = queryParams;
        this.presenter = presenter;
        this.queryBus = queryBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET_MANY_COMPANY_TRAITS, tags = {SWAGGER_DOC_TAG})
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
    })
    @RequestMapping(value = TalentHttpRouter.TALENT_COMPANYTRAITS_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = CompanyTraitsPaginatedResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CompanyTraitsUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CompanyTraitsForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = CompanyTraitsNotFound.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable String id) {

        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();

        final FieldsOptions fieldsOptions = queryParams.getFields();
        final FilterOptions filterOptions = queryParams.getFilters();
        final PageOptions pageOptions = queryParams.getPage();
        final SortOptions sortOptions = queryParams.getSort();

        return Mono.fromFuture(queryBus.dispatch(new CompanyTraitsTalentPaginated.Query(id, fieldsOptions, filterOptions, pageOptions, sortOptions)))
            .map(page -> paginatedView((Paginated<CompanyTraits>) page, request, pageOptions))
            .doOnError(this::errorView)
            .toFuture();
    }

    private ResponseEntity<String> paginatedView(final Paginated<CompanyTraits> page,
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

            metrics.incrementTalentCompanyTraitsSuccess();
            return ResponseEntity.ok().headers(headers).body(responseBody);

        } catch (Throwable e) {
            metrics.incrementTalentCompanyTraitsError();
            return new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentCompanyTraitsError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
