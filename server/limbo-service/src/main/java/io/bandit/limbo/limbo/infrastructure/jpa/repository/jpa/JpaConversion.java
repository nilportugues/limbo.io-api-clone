package io.bandit.limbo.limbo.infrastructure.jpa.repository.jpa;

import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;
import io.bandit.limbo.limbo.modules.shared.model.PageOptions;
import io.bandit.limbo.limbo.modules.shared.model.SortOptions;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;



public class JpaConversion {

    private static JpaFilteringProvider filteringProvider;

    public JpaConversion(final JpaFilteringProvider provider) {
        filteringProvider = provider;
    }

    public Pageable fromPage(final PageOptions page, final SortOptions sortOptions) {
        return new PageRequest(page.getNumber() - 1, page.getSize(), fromSorting(sortOptions));
    }

    public Pageable fromPage(final PageOptions page) {
        return new PageRequest(page.getNumber() - 1, page.getSize());
    }

    public Specification fromFilters(final FilterOptions filterOptions) {
        return filteringProvider.specification(filterOptions);
    }

    public Sort fromSorting(final SortOptions sortOptions) {

        Sort sorting = null;

        final List<String> ascending = sortOptions.getAscending();
        if (!ascending.isEmpty()) {
            sorting = new Sort(Sort.Direction.ASC, ascending);
        }

        final List<String> descending = sortOptions.getDescending();
        if (!descending.isEmpty()) {
            if (null == sorting) {
                sorting = new Sort(Sort.Direction.DESC, descending);
            } else {
                sorting.and(new Sort(Sort.Direction.DESC, descending));
            }
        }

        return sorting;
    }
}

