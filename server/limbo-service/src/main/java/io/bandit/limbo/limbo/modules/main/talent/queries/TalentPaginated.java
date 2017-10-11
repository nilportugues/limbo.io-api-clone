package io.bandit.limbo.limbo.modules.main.talent.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.shared.model.*;
import org.springframework.data.domain.Page;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class TalentPaginated {

    public static class Query implements IQuery {
        private final FieldsOptions fields;
        private final FilterOptions filters;
        private final PageOptions page;
        private final SortOptions sort;

        public Query(final FieldsOptions fields,
                     final FilterOptions filters,
                     final PageOptions page,
                     final SortOptions sort) {

            this.fields = fields;
            this.filters = filters;
            this.page = page;
            this.sort = sort;
        }

        public FieldsOptions getFields() {
            return fields;
        }

        public FilterOptions getFilters() {
            return filters;
        }

        public PageOptions getPage() {
            return page;
        }

        public SortOptions getSort() {
            return sort;
        }
    }

    @Named("TalentPaginated.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final TalentRepository repository;

        @Inject
        public QueryHandler(final TalentRepository repository) {
            this.repository = repository;
        }

        /**
         * Get Talent as pages.
         *
         * @return a page of Talent entities
         */
        public CompletableFuture<Paginated<Talent>> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> {
                final Page<Talent> result = repository.findAll(
                        query.getFilters(),
                        query.getPage(),
                        query.getSort());

                return  new Paginated<>(
                        result.getContent(),
                       result.getTotalPages(),
                       result.getTotalElements(),
                       result.getNumber()+1,
                       result.getSize());
            });
        }
    }
}
