package io.bandit.limbo.limbo.infrastructure.jpa.repository.elasticsearch;

import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;
import org.elasticsearch.index.query.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class ElasticSearchFilterAdapter
{
    @SuppressWarnings("unchecked")
    public BoolQueryBuilder toQueryBuilder(final FilterOptions filter) {

        final BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        Set range = filter.getRanges().entrySet();
        if (1 == range.size()) {
            Entry entry = (Entry) range.toArray()[0];
            ArrayList<String> data = (ArrayList<String>) entry.getValue();

            String start = data.get(0);
            String end = data.get(1);

            if (start != null && end != null) {
                RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(entry.getKey().toString());
                rangeQuery.from(start).to(end);
                boolQueryBuilder.must(rangeQuery);
            }
        }

        Set notRanges = filter.getNotRanges().entrySet();
        if (1 == notRanges.size()) {
            Entry entry = (Entry) notRanges.toArray()[0];
            ArrayList<String> data = (ArrayList<String>) entry.getValue();

            String start = data.get(0);
            String end = data.get(1);

            if (start != null && end != null) {
                RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(entry.getKey().toString());
                rangeQuery.lte(start);
                rangeQuery.gte(end);
                boolQueryBuilder.must(rangeQuery);
            }
        }

        filter.getEquals().entrySet().forEach(entry -> {
            final Entry<String, List> castedEntry = (Entry) entry;
            castedEntry.getValue().forEach(value -> {
                final MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(castedEntry.getKey(), castedEntry.getValue());
                boolQueryBuilder.must(matchQueryBuilder);
            });
        });

        filter.getNot().entrySet().forEach(entry -> {
            final Entry<String, List> castedEntry = (Entry) entry;
            castedEntry.getValue().forEach(value -> {
                final MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(castedEntry.getKey(), castedEntry.getValue());
                boolQueryBuilder.mustNot(matchQueryBuilder);
            });
        });

        filter.getEnds().entrySet().forEach(entry -> {
            final Entry<String, List> castedEntry = (Entry) entry;
            castedEntry.getValue().forEach(value -> {
                    final String endsRegex = "(.*)?("+value+")";
                    final RegexpQueryBuilder regexpQueryBuilder = QueryBuilders.regexpQuery(castedEntry.getKey(), endsRegex);
                    boolQueryBuilder.must(regexpQueryBuilder);
                }
            );
        });

        filter.getNotEnds().entrySet().forEach(entry -> {
            final Entry<String, List> castedEntry = (Entry) entry;
            castedEntry.getValue().forEach(value -> {
                    final String endsRegex = "(.*)?("+value+")";
                    final RegexpQueryBuilder regexpQueryBuilder = QueryBuilders.regexpQuery(castedEntry.getKey(), endsRegex);
                    boolQueryBuilder.mustNot(regexpQueryBuilder);
                }
            );
        });

        filter.getStarts().entrySet().forEach(entry -> {
            final Entry<String, List> castedEntry = (Entry) entry;
            castedEntry.getValue().forEach(value -> {
                    final String startsRegex = "("+value+")(.*)?";
                    final RegexpQueryBuilder regexpQueryBuilder = QueryBuilders.regexpQuery(castedEntry.getKey(), startsRegex);
                    boolQueryBuilder.must(regexpQueryBuilder);
                }
            );
        });

        filter.getNotStarts().entrySet().forEach(entry -> {
            final Entry<String, List> castedEntry = (Entry) entry;
            castedEntry.getValue().forEach(value -> {
                    final String startsRegex = "("+value+")(.*)?";
                    final RegexpQueryBuilder regexpQueryBuilder = QueryBuilders.regexpQuery(castedEntry.getKey(), startsRegex);
                    boolQueryBuilder.mustNot(regexpQueryBuilder);
                }
            );
        });


        filter.getHas().entrySet().forEach(entry -> {
            final Entry<String, List> castedEntry = (Entry) entry;
            castedEntry.getValue().forEach(value -> {
                    final String containsRegex = "(.*)?("+value+")(.*)?";
                    final RegexpQueryBuilder regexpQueryBuilder = QueryBuilders.regexpQuery(castedEntry.getKey(), containsRegex);
                    boolQueryBuilder.must(regexpQueryBuilder);
                }
            );
        });


        filter.getNotHas().entrySet().forEach(entry -> {
            final Entry<String, List> castedEntry = (Entry) entry;
            castedEntry.getValue().forEach(value -> {
                    final String containsRegex = "(.*)?("+value+")(.*)?";
                    final RegexpQueryBuilder regexpQueryBuilder = QueryBuilders.regexpQuery(castedEntry.getKey(), containsRegex);
                    boolQueryBuilder.mustNot(regexpQueryBuilder);
                }
            );
        });


        filter.getGreaterThan().entrySet().forEach(entry -> {
            final Entry<String, List> castedEntry = (Entry) entry;
            castedEntry.getValue().forEach(value -> {
                    final RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(castedEntry.getKey());
                    rangeQuery.gt(castedEntry.getValue());
                    boolQueryBuilder.must(rangeQuery);
                }
            );
        });

        filter.getGreaterThanOrEqual().entrySet().forEach(entry -> {
            final Entry<String, List> castedEntry = (Entry) entry;
            castedEntry.getValue().forEach(value -> {
                    final RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(castedEntry.getKey());
                    rangeQuery.gte(castedEntry.getValue());
                    boolQueryBuilder.must(rangeQuery);
                }
            );
        });

        filter.getLessThan().entrySet().forEach(entry -> {
            final Entry<String, List> castedEntry = (Entry) entry;
            castedEntry.getValue().forEach(value -> {
                    final RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(castedEntry.getKey());
                    rangeQuery.lt(castedEntry.getValue());
                    boolQueryBuilder.must(rangeQuery);
                }
            );
        });

        filter.getLessThanOrEqual().entrySet().forEach(entry -> {
            final Entry<String, List> castedEntry = (Entry) entry;
            castedEntry.getValue().forEach(value -> {
                    final RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(castedEntry.getKey());
                    rangeQuery.lte(castedEntry.getValue());
                    boolQueryBuilder.must(rangeQuery);
                }
            );
        });

        filter.getIn().entrySet().forEach(entry -> {
            final Entry<String, List> castedEntry = (Entry) entry;
            castedEntry.getValue().forEach(value -> {
                    final MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(castedEntry.getKey(), castedEntry.getValue());
                    boolQueryBuilder.must(matchQueryBuilder);
                }
            );
        });

        filter.getNotIn().entrySet().forEach(entry -> {
            final Entry<String, List> castedEntry = (Entry) entry;
            castedEntry.getValue().forEach(value -> {
                    final MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(castedEntry.getKey(), castedEntry.getValue());
                    boolQueryBuilder.mustNot(matchQueryBuilder);
                }
            );
        });


        return boolQueryBuilder;
    }
}
