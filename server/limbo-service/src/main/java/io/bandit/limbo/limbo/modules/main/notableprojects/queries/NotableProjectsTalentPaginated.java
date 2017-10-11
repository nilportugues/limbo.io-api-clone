package io.bandit.limbo.limbo.modules.main.notableprojects.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.NotableProjectsRepository;
import io.bandit.limbo.limbo.modules.shared.model.*;
import org.springframework.data.domain.Page;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class NotableProjectsTalentPaginated {

    public static class Query implements IQuery {
        private final String id;
        private final FieldsOptions fields;
        private final FilterOptions filters;
        private final PageOptions page;
        private final SortOptions sort;

        public Query(final String id,
                     final FieldsOptions fields,
                     final FilterOptions filters,
                     final PageOptions page,
                     final SortOptions sort) {

            this.id = id;
            this.fields = fields;
            this.filters = filters;
            this.page = page;
            this.sort = sort;
        }

        public String getId() {
            return this.id;
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

    @Named("NotableProjectsTalentPaginated.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final NotableProjectsRepository notableProjectsRepository;

        @Inject
        public QueryHandler(final NotableProjectsRepository notableProjectsRepository) {
            this.notableProjectsRepository = notableProjectsRepository;
        }

        /**
         * Get NotableProjects as pages given a Talent id.
         *
         * @return a page of NotableProjects entities
         */
        public CompletableFuture<Paginated<NotableProjects>> handle(final Query query) {
            return CompletableFuture.supplyAsync(() -> {
                final Page<NotableProjects> result = notableProjectsRepository.findByTalent(
                        query.getId(),
                        query.getFilters(),
                        query.getPage(),
                        query.getSort());

                return new Paginated<>(
                        result.getContent(),
                        result.getTotalPages(),
                        result.getTotalElements(),
                        result.getNumber()+1,
                        result.getSize());
            });
        }
    }
}
