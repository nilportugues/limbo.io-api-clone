package io.bandit.limbo.limbo.application.api.resources.talent;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.main.joboffer.queries.JobOfferTalentPaginated;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.joboffer.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.metrics.TalentApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.joboffer.middleware.JobOfferQueryParams;
import io.bandit.limbo.limbo.application.api.resources.joboffer.presenters.JobOfferOutputBoundary;
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
public class GetTalentJobOfferResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_GET_MANY_JOB_OFFERS= "Gets the JobOffer for the current Talents.";

    private final IQueryBus queryBus;
    private final JobOfferQueryParams queryParams;
    private final JobOfferOutputBoundary presenter;
    private final TalentApiMetrics metrics;

    @Inject
    public GetTalentJobOfferResource(final IQueryBus queryBus,
            final JobOfferQueryParams queryParams,
            @Named("JobOfferJsonPresenter") final JobOfferOutputBoundary presenter,
            final TalentApiMetrics metrics) {

        this.queryParams = queryParams;
        this.presenter = presenter;
        this.queryBus = queryBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_GET_MANY_JOB_OFFERS, tags = {SWAGGER_DOC_TAG})
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

        //Filters for: salaryMax
        @ApiImplicitParam(name="filter[salary-max]", dataType = "string", paramType = "query", value="**Salary Max** matches this value."),
        @ApiImplicitParam(name="filter[not][salary-max]", dataType = "string", paramType = "query", value="**Salary Max** does not match this value."),
        @ApiImplicitParam(name="filter[ranges][salary-max]", dataType = "string", paramType = "query", value="**Salary Max** ranges between a min and a max. Separated by commas."),
        @ApiImplicitParam(name="filter[!ranges][salary-max]", dataType = "string", paramType = "query", value="**Salary Max** values that do not range between the min and max. Separated by commas."),
        @ApiImplicitParam(name="filter[in][salary-max]", dataType = "string", paramType = "query", value="**Salary Max** matches any of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[!in][salary-max]", dataType = "string" , paramType = "query", value="**Salary Max** matching none of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[gte][salary-max]", dataType = "string", paramType = "query", value="**Salary Max** is greater than or equals this value."),
        @ApiImplicitParam(name="filter[gt][salary-max]", dataType = "string", paramType = "query", value="**Salary Max** is greater than this value."),
        @ApiImplicitParam(name="filter[lte][salary-max]", dataType = "string", paramType = "query", value="**Salary Max** is less than or equals this value."),
        @ApiImplicitParam(name="filter[lt][salary-max]", dataType = "string", paramType = "query", value="**Salary Max** is less than this value."),
        @ApiImplicitParam(name="filter[has][salary-max]", dataType = "string", paramType = "query", value="**Salary Max** contains this value."),
        @ApiImplicitParam(name="filter[!has][salary-max]", dataType = "string", paramType = "query", value="**Salary Max** does not contain this value."),
        @ApiImplicitParam(name="filter[starts][salary-max]", dataType = "string", paramType = "query", value="**Salary Max** starts with this value."),
        @ApiImplicitParam(name="filter[ends][salary-max]", dataType = "string", paramType = "query", value="**Salary Max** does not start with this value."),
        @ApiImplicitParam(name="filter[!starts][salary-max]", dataType = "string", paramType = "query", value="**Salary Max** ends with this value."),
        @ApiImplicitParam(name="filter[!ends][salary-max]", dataType = "string", paramType = "query", value="**Salary Max** does not end with this value."),

        //Filters for: salaryMin
        @ApiImplicitParam(name="filter[salary-min]", dataType = "string", paramType = "query", value="**Salary Min** matches this value."),
        @ApiImplicitParam(name="filter[not][salary-min]", dataType = "string", paramType = "query", value="**Salary Min** does not match this value."),
        @ApiImplicitParam(name="filter[ranges][salary-min]", dataType = "string", paramType = "query", value="**Salary Min** ranges between a min and a max. Separated by commas."),
        @ApiImplicitParam(name="filter[!ranges][salary-min]", dataType = "string", paramType = "query", value="**Salary Min** values that do not range between the min and max. Separated by commas."),
        @ApiImplicitParam(name="filter[in][salary-min]", dataType = "string", paramType = "query", value="**Salary Min** matches any of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[!in][salary-min]", dataType = "string" , paramType = "query", value="**Salary Min** matching none of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[gte][salary-min]", dataType = "string", paramType = "query", value="**Salary Min** is greater than or equals this value."),
        @ApiImplicitParam(name="filter[gt][salary-min]", dataType = "string", paramType = "query", value="**Salary Min** is greater than this value."),
        @ApiImplicitParam(name="filter[lte][salary-min]", dataType = "string", paramType = "query", value="**Salary Min** is less than or equals this value."),
        @ApiImplicitParam(name="filter[lt][salary-min]", dataType = "string", paramType = "query", value="**Salary Min** is less than this value."),
        @ApiImplicitParam(name="filter[has][salary-min]", dataType = "string", paramType = "query", value="**Salary Min** contains this value."),
        @ApiImplicitParam(name="filter[!has][salary-min]", dataType = "string", paramType = "query", value="**Salary Min** does not contain this value."),
        @ApiImplicitParam(name="filter[starts][salary-min]", dataType = "string", paramType = "query", value="**Salary Min** starts with this value."),
        @ApiImplicitParam(name="filter[ends][salary-min]", dataType = "string", paramType = "query", value="**Salary Min** does not start with this value."),
        @ApiImplicitParam(name="filter[!starts][salary-min]", dataType = "string", paramType = "query", value="**Salary Min** ends with this value."),
        @ApiImplicitParam(name="filter[!ends][salary-min]", dataType = "string", paramType = "query", value="**Salary Min** does not end with this value."),

        //Filters for: salaryCurrency
        @ApiImplicitParam(name="filter[salary-currency]", dataType = "string", paramType = "query", value="**Salary Currency** matches this value."),
        @ApiImplicitParam(name="filter[not][salary-currency]", dataType = "string", paramType = "query", value="**Salary Currency** does not match this value."),
        @ApiImplicitParam(name="filter[ranges][salary-currency]", dataType = "string", paramType = "query", value="**Salary Currency** ranges between a min and a max. Separated by commas."),
        @ApiImplicitParam(name="filter[!ranges][salary-currency]", dataType = "string", paramType = "query", value="**Salary Currency** values that do not range between the min and max. Separated by commas."),
        @ApiImplicitParam(name="filter[in][salary-currency]", dataType = "string", paramType = "query", value="**Salary Currency** matches any of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[!in][salary-currency]", dataType = "string" , paramType = "query", value="**Salary Currency** matching none of the provided values. Separated by commas."),
        @ApiImplicitParam(name="filter[gte][salary-currency]", dataType = "string", paramType = "query", value="**Salary Currency** is greater than or equals this value."),
        @ApiImplicitParam(name="filter[gt][salary-currency]", dataType = "string", paramType = "query", value="**Salary Currency** is greater than this value."),
        @ApiImplicitParam(name="filter[lte][salary-currency]", dataType = "string", paramType = "query", value="**Salary Currency** is less than or equals this value."),
        @ApiImplicitParam(name="filter[lt][salary-currency]", dataType = "string", paramType = "query", value="**Salary Currency** is less than this value."),
        @ApiImplicitParam(name="filter[has][salary-currency]", dataType = "string", paramType = "query", value="**Salary Currency** contains this value."),
        @ApiImplicitParam(name="filter[!has][salary-currency]", dataType = "string", paramType = "query", value="**Salary Currency** does not contain this value."),
        @ApiImplicitParam(name="filter[starts][salary-currency]", dataType = "string", paramType = "query", value="**Salary Currency** starts with this value."),
        @ApiImplicitParam(name="filter[ends][salary-currency]", dataType = "string", paramType = "query", value="**Salary Currency** does not start with this value."),
        @ApiImplicitParam(name="filter[!starts][salary-currency]", dataType = "string", paramType = "query", value="**Salary Currency** ends with this value."),
        @ApiImplicitParam(name="filter[!ends][salary-currency]", dataType = "string", paramType = "query", value="**Salary Currency** does not end with this value."),
    })
    @RequestMapping(value = TalentHttpRouter.TALENT_JOBOFFER_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = JobOfferPaginatedResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = JobOfferUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = JobOfferForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = JobOfferNotFound.class)
    })
    public Future<ResponseEntity<String>> get(@PathVariable String id) {

        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();

        final FieldsOptions fieldsOptions = queryParams.getFields();
        final FilterOptions filterOptions = queryParams.getFilters();
        final PageOptions pageOptions = queryParams.getPage();
        final SortOptions sortOptions = queryParams.getSort();

        return Mono.fromFuture(queryBus.dispatch(new JobOfferTalentPaginated.Query(id, fieldsOptions, filterOptions, pageOptions, sortOptions)))
            .map(page -> paginatedView((Paginated<JobOffer>) page, request, pageOptions))
            .doOnError(this::errorView)
            .toFuture();
    }

    private ResponseEntity<String> paginatedView(final Paginated<JobOffer> page,
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

            metrics.incrementTalentJobOfferSuccess();
            return ResponseEntity.ok().headers(headers).body(responseBody);

        } catch (Throwable e) {
            metrics.incrementTalentJobOfferError();
            return new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentJobOfferError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
