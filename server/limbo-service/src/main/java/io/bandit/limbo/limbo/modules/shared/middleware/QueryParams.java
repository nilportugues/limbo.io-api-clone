package io.bandit.limbo.limbo.modules.shared.middleware;

import io.bandit.limbo.limbo.modules.shared.model.FieldsOptions;
import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;
import io.bandit.limbo.limbo.modules.shared.model.PageOptions;
import io.bandit.limbo.limbo.modules.shared.model.SortOptions;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

abstract public class QueryParams {

    private HttpServletRequest context;
    private FilterTransformer filterTransformer;

    public QueryParams(HttpServletRequest context, FilterTransformer filterTransformer) {
        this.context = context;
        this.filterTransformer = filterTransformer;
    }

    public PageOptions getPage() {
        final PageOptions pageOptions = PageOptions.forNumericPage(1, 10);
        buildPageQueryParams(pageOptions);
        return pageOptions;
    }

    public FilterOptions getFilters() {
        final FilterOptions filters = new FilterOptions();
        buildFilterQueryParams(filters);
        return filters;
    }

    public FieldsOptions getFields() {
        return new FieldsOptions();
    }

    public SortOptions getSort() {
        final SortOptions sortOptions = new SortOptions();
        buildSortQueryParams(sortOptions);
        return sortOptions;
    }

    private void buildSortQueryParams(SortOptions sortOptions) {

        for (Map.Entry<String, String[]> value : getParameterEntries()) {
            for (String v : value.getValue()) {
                if (value.getKey().equals("sort")) {
                    for (String sorting : Arrays.asList(v.split(","))) {
                        if ('-' == sorting.charAt(0)) {
                            String fieldName = filterTransformer.getTransformedKey(sorting.substring(1));
                            if (null != fieldName) {
                                sortOptions.addDescending(fieldName);
                            }
                        } else {
                            String fieldName = filterTransformer.getTransformedKey(sorting);
                            if (null != fieldName) {
                                sortOptions.addAscending(fieldName);
                            }
                        }
                    }
                }
            }
        }
    }


    private Set<Map.Entry<String, String[]>> getParameterEntries() {
        return context.getParameterMap().entrySet();
    }

    private void buildPageQueryParams(PageOptions pageOptions) {

        for (Map.Entry<String, String[]> value : getParameterEntries()) {
            for (String v : value.getValue()) {
                switch (value.getKey()) {
                    case "page[number]":
                        pageOptions.setNumber(v);
                        break;
                    case "page[limit]":
                        pageOptions.setLimit(v);
                        break;
                    case "page[cursor]":
                        pageOptions.setCursor(v);
                        break;
                    case "page[offset]":
                        pageOptions.setOffset(v);
                        break;
                    case "page[size]":
                        pageOptions.setSize(v);
                        break;
                }
            }
        }
    }

    /**
     * Parses the URI looking for message* properties and feeds the FilterOptions object with data.
     */
    private void buildFilterQueryParams(FilterOptions filters) {

        getParameterEntries().iterator().forEachRemaining(entry -> {

            String inputKey = null;
            String fieldName = null;

            if (entry.getKey().contains("[") && entry.getKey().startsWith("filter")) {
                inputKey = valueFromBrackets(entry.getKey());
                fieldName = filterTransformer.getTransformedKey(inputKey);
            }

            if (null != fieldName) {
                String filterParameter = entry.getKey().replace("[" + inputKey + "]", "");
                buildFilter(filters, inputKey, fieldName, filterParameter, entry);
            }
        });
    }

    /**
     * @param queryString   The raw string.
     * @return              Parameter inside the brackets.
     */
    private String valueFromBrackets(String queryString) {

        int start = queryString.lastIndexOf('[');
        int end = queryString.lastIndexOf(']');

        return queryString.substring(start+1, end);
    }

    @SuppressWarnings("unchecked")
    private void buildFilter(FilterOptions filters, String presenterKey, String currentKey, String filterParameter, Map.Entry<String, String[]> value){

        switch (filterParameter) {
            case "filter[not]":
                filters.setNot(
                    currentKey,
                    new ArrayList<>(Arrays.asList(hydrateStrings(presenterKey, value)))
                );
                break;
            case "filter[ranges]":
                filters.setRanges(
                    currentKey,
                    new ArrayList<>(Arrays.asList(splitStringAndHydrate(presenterKey, value)))
                );
                break;
            case "filter[!ranges]":
                filters.setNotRanges(
                    currentKey,
                    new ArrayList<>(Arrays.asList(splitStringAndHydrate(presenterKey, value)))
                );
                break;
            case "filter[in]":
                filters.setIn(
                    currentKey,
                    new ArrayList<>(Arrays.asList(splitStringAndHydrate(presenterKey, value)))
                );
                break;
            case "filter[!in]":
                filters.setNotIn(
                    currentKey,
                    new ArrayList<>(Arrays.asList(splitStringAndHydrate(presenterKey, value)))
                );
                break;
            case "filter[gte]":
                filters.setGreaterThanOrEqual(
                    currentKey,
                    new ArrayList<>(Arrays.asList(hydrateStrings(presenterKey, value)))
                );
                break;
            case "filter[gt]":
                filters.setGreaterThan(
                    currentKey,
                    new ArrayList<>(Arrays.asList(hydrateStrings(presenterKey, value)))
                );
                break;
            case "filter[lte]":
                filters.setLessThanOrEqual(
                    currentKey,
                    new ArrayList<>(Arrays.asList(hydrateStrings(presenterKey, value)))
                );
                break;
            case "filter[lt]":
                filters.setLessThan(
                    currentKey,
                    new ArrayList<>(Arrays.asList(hydrateStrings(presenterKey, value)))
                );
                break;
            case "filter[has]":
                filters.setHas(
                    currentKey,
                    new ArrayList<>(Arrays.asList(hydrateStrings(presenterKey, value)))
                );
                break;
            case "filter[!has]":
                filters.setNotHas(
                    currentKey,
                    new ArrayList<>(Arrays.asList(hydrateStrings(presenterKey, value)))
                );
                break;
            case "filter[starts]":
                filters.setStarts(
                    currentKey,
                    new ArrayList<>(Arrays.asList(hydrateStrings(presenterKey, value)))
                );
                break;
            case "filter[ends]":
                filters.setEnds(
                    currentKey,
                    new ArrayList<>(Arrays.asList(hydrateStrings(presenterKey, value)))
                );
                break;
            case "filter[!starts]":
                filters.setNotStarts(
                    currentKey,
                    new ArrayList<>(Arrays.asList(hydrateStrings(presenterKey, value)))
                );
                break;
            case "filter[!ends]":
                filters.setNotEnds(
                    currentKey,
                    new ArrayList<>(Arrays.asList(hydrateStrings(presenterKey, value)))
                );
                break;
            default:

                if (filterParameter.equals("filter")) {
                    filters.setEquals(
                        currentKey,
                        new ArrayList<>(Arrays.asList(hydrateStrings(presenterKey, value)))
                    );
                }
                break;
        }
    }

    private Object[] splitStringAndHydrate(String presenterKey, Map.Entry<String, String[]> value) {
        final List<String> values = new ArrayList<>();

        for (int i = 0; i<value.getValue().length; i++) {
            values.addAll(Arrays.asList(value.getValue()[i].split(",")));
        }

        return hydrateArray(presenterKey, values.toArray());
    }

    private Object[] hydrateStrings(String presenterKey, Map.Entry<String, String[]> value) {
        return hydrateArray(presenterKey, value.getValue());
    }

    private Object[] hydrateArray(String presenterKey, Object[] value) {
        Object[] newValues = new Object[value.length];

        for (int i = 0; i<value.length; i++) {
            newValues[i] = filterTransformer.getTransformationValue(presenterKey, value[i]);
        }

        return newValues;
    }
}
