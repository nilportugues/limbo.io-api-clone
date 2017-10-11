package io.bandit.limbo.limbo.infrastructure.jpa.repository.jpa;

import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;
import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

class JpaFiltering implements Specification {

    private FilterOptions filter;
    private TypeConverter typeConverter;

    public JpaFiltering(final TypeConverter typeConverter, final FilterOptions filter) {
        this.typeConverter = typeConverter;
        this.filter = filter;
    }

    @SuppressWarnings("unchecked")
    private List<Predicate> predicatesFromFilter(final Root root, final CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        filter.getEquals().entrySet().forEach(entry -> {
                final Entry<String, List> castedEntry = (Entry) entry;
                castedEntry.getValue().forEach(value ->
                    predicates.add(criteriaBuilder.equal(root.get(castedEntry.getKey()), value))
                );
            }
        );

        filter.getNot().entrySet().forEach(entry -> {
                final Entry<String, List> castedEntry = (Entry) entry;
                castedEntry.getValue().forEach(value ->
                    predicates.add(
                        criteriaBuilder.notEqual(root.get(castedEntry.getKey()), value)
                    )
                );
            }

        );

        filter.getEnds().entrySet().forEach(entry -> {
                final Entry<String, List> castedEntry = (Entry) entry;
                castedEntry.getValue().forEach(value ->
                    predicates.add(
                        criteriaBuilder.like(root.get(castedEntry.getKey()), "%" + value)
                    )
                );
            }
        );

        filter.getNotEnds().entrySet().forEach(entry -> {
                final Entry<String, List> castedEntry = (Entry) entry;
                castedEntry.getValue().forEach(value ->
                    predicates.add(
                        criteriaBuilder.notLike(root.get(castedEntry.getKey()), "%" + value)
                    )
                );
            }
        );

        filter.getStarts().entrySet().forEach(entry -> {
                final Entry<String, List> castedEntry = (Entry) entry;
                castedEntry.getValue().forEach(value ->
                    predicates.add(
                        criteriaBuilder.like(root.get(castedEntry.getKey()), value + "%")
                    )
                );
            }
        );

        filter.getNotStarts().entrySet().forEach(entry -> {
                final Entry<String, List> castedEntry = (Entry) entry;
                castedEntry.getValue().forEach(value ->
                    predicates.add(criteriaBuilder.notLike(root.get(castedEntry.getKey()), value + "%"))
                );
            }
        );

        filter.getGreaterThan().entrySet().forEach(entry -> {
                final Entry<String, List> castedEntry = (Entry) entry;
                castedEntry.getValue().forEach(value -> {
                        predicates.add(criteriaBuilder.greaterThan(root.get(castedEntry.getKey()), (Comparable) value));
                    }
                );
            }
        );

        filter.getGreaterThanOrEqual().entrySet().forEach(entry -> {
                final Entry<String, List> castedEntry = (Entry) entry;
                castedEntry.getValue().forEach(value ->
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(castedEntry.getKey()), (Comparable) value))
                );
            }
        );

        filter.getLessThan().entrySet().forEach(entry -> {
                final Entry<String, List> castedEntry = (Entry) entry;
                castedEntry.getValue().forEach(value ->
                    predicates.add(criteriaBuilder.lessThan(root.get(castedEntry.getKey()), (Comparable) value))
                );
            }
        );

        filter.getLessThanOrEqual().entrySet().forEach(entry -> {
                final Entry<String, List> castedEntry = (Entry) entry;
                castedEntry.getValue().forEach(value ->
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(castedEntry.getKey()), toComparable(value)))
                );
            }
        );

        filter.getHas().entrySet().forEach(entry -> {
                final Entry<String, List> castedEntry = (Entry) entry;
                castedEntry.getValue().forEach(value ->
                    predicates.add(criteriaBuilder.like(root.get(castedEntry.getKey()), "%" + value + "%"))
                );
            }
        );

        filter.getNotHas().entrySet().forEach(entry -> {
                final Entry<String, List> castedEntry = (Entry) entry;
                castedEntry.getValue().forEach(value ->
                    predicates.add(criteriaBuilder.notLike(root.get(castedEntry.getKey()), "%" + value + "%"))
                );
            }
        );


        filter.getIn().entrySet().forEach(entry -> {
                final Entry<String, List> castedEntry = (Entry) entry;
                predicates.add(root.get(castedEntry.getKey()).in(castedEntry.getValue()));
            }
        );


        filter.getNotIn().entrySet().forEach(entry -> {
                final Entry<String, List> castedEntry = (Entry) entry;
                predicates.add(criteriaBuilder.not(
                    root.get(castedEntry.getKey()).in(castedEntry.getValue()))
                );
            }
        );


        final Set range = filter.getRanges().entrySet();
        if (1 == range.size()) {
            Entry<String, List> entry = (Entry) range.toArray()[0];
            ArrayList<String> data = (ArrayList<String>) entry.getValue();

            Comparable start = toComparable(data.get(0));
            Comparable end = toComparable(data.get(1));

            if (start != null && end != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(entry.getKey()), start));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(entry.getKey()), end));
            }
        }

        final Set notRanges = filter.getNotRanges().entrySet();
        if (1 == notRanges.size()) {
            Entry<String, List> entry = (Entry) range.toArray()[0];
            ArrayList<String> data = (ArrayList<String>) entry.getValue();
            Comparable start = toComparable(data.get(0));
            Comparable end = toComparable(data.get(1));

            if (start != null && end != null) {
                predicates.add(criteriaBuilder.not(criteriaBuilder.greaterThanOrEqualTo(root.get(entry.getKey()), start)));
                predicates.add(criteriaBuilder.not(criteriaBuilder.lessThanOrEqualTo(root.get(entry.getKey()), end)));
            }
        }


        return predicates;
    }

    /**
     * This is where the magic happens for comparision operations. Comparable interface is what makes it work.
     *
     * @param object  Object to convert into a Comparable
     * @return        We cast to a String value, because it's comparable using a smart Type Converter
     */
    private Comparable toComparable(final Object object) {

        if (object instanceof Date || object instanceof LocalTime || object instanceof ZonedDateTime) {
            return (Comparable) object;
        }

        return (Comparable) typeConverter.convert(String.class.getName(), object);
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

        final List<Predicate> list = predicatesFromFilter(root, criteriaBuilder);
        final Predicate[] predicates = new Predicate[list.size()];

        for (int i = 0; i < list.size(); i++) {
            predicates[i] = list.get(i);
        }

        return criteriaBuilder.and(predicates);
    }
}
