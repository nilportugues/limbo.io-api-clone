package io.bandit.limbo.limbo.modules.cqrs;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.modules.cqrs.middleware.QueryCounterMetricsMiddleware;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryBusMiddleware
{
    @Bean
    public QueryCounterMetricsMiddleware queryCounterMetricsMiddleware(final IQueryBus queryBus,
                                                                       final MeterRegistry registry) {

        final QueryCounterMetricsMiddleware middleware = new QueryCounterMetricsMiddleware(registry);
        queryBus.addMiddleware(middleware);

        return middleware;
    }

}