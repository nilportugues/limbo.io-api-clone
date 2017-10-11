package io.bandit.limbo.limbo.modules.main.worktype.infrastructure;

import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.jpa.WorkTypeJpaModel;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.jpa.WorkTypeJpaRepository;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.jpa.WorkTypeJpaSpecification;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.jpa.WorkTypeJpaMapper;
import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;
import io.bandit.limbo.limbo.modules.shared.model.PageOptions;
import io.bandit.limbo.limbo.modules.shared.model.SortOptions;
import io.bandit.limbo.limbo.infrastructure.jpa.repository.jpa.JpaConversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Named("WorkTypeRepository")
@Transactional //Hibernate session depends on this.
public class WorkTypeRepository {

    private final WorkTypeJpaMapper mapper;
    private final WorkTypeJpaRepository writeRepository;
    private final JpaConversion jpaConversion;

    @Inject
    public WorkTypeRepository(final WorkTypeJpaMapper mapper,
                                    final WorkTypeJpaRepository writeRepository,
                                    final JpaConversion jpaConversion) {

        this.mapper = mapper;
        this.writeRepository = writeRepository;
        this.jpaConversion = jpaConversion;
    }

    public WorkType findOne(final String id) {
        return mapper.toDomain(writeRepository.findOne(id));
    }

    public Page<WorkType> findAll(final PageOptions pageOptions, final SortOptions sortOptions) {
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<WorkTypeJpaModel> modelPage = writeRepository.findAll(pageable);
        final List<WorkType> list = modelPage.getContent()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    public WorkType save(final WorkType workType) {
        final WorkTypeJpaModel workTypeModel = writeRepository.save(mapper.toModel(workType));

        return mapper.toDomain(workTypeModel);
    }

    public List<WorkType> findAll() {
        final List<WorkTypeJpaModel> list = writeRepository.findAll();

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<WorkType> findAll(final SortOptions sortOptions) {
        final List<WorkTypeJpaModel> list = writeRepository.findAll(jpaConversion.fromSorting(sortOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<WorkType> findAll(final Iterable<String> iterable) {
        final List<WorkTypeJpaModel> list = writeRepository.findAll(iterable);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<WorkType> findAll(final FilterOptions filterOptions) {
        final List<WorkTypeJpaModel> list = writeRepository.findAll(jpaConversion.fromFilters(filterOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public Page<WorkType> findAll(final FilterOptions filterOptions,
                                            final PageOptions pageOptions,
                                            final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<WorkTypeJpaModel> modelPage = writeRepository.findAll(specification, pageable);
        final List<WorkType> list = modelPage.getContent()
            .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    @SuppressWarnings("unchecked")
    public List<WorkType> findAll(final FilterOptions filterOptions, final SortOptions sortOptions) {
        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Sort sort = jpaConversion.fromSorting(sortOptions);
        final List<WorkTypeJpaModel> list = writeRepository.findAll(specification, sort);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public void flush() {
        writeRepository.flush();
    }

    public List<WorkType> save(final Iterable<WorkType> iterable) {
        final ArrayList<WorkTypeJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        final List<WorkTypeJpaModel> list = writeRepository.save(models);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }


    public WorkType saveAndFlush(final WorkType workType) {
        final WorkTypeJpaModel workTypeModel = writeRepository.saveAndFlush(mapper.toModel(workType));

        return mapper.toDomain(workTypeModel);
    }

    public void deleteInBatch(final Iterable<WorkType> iterable) {
        final ArrayList<WorkTypeJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.deleteInBatch(models);
    }

    public void deleteAllInBatch() {
        writeRepository.deleteAllInBatch();
    }

    public WorkType getOne(final String id) {
        return this.findOne(id);
    }

    @SuppressWarnings("unchecked")
    public long count(final FilterOptions filterOptions) {
        return writeRepository.count(jpaConversion.fromFilters(filterOptions));
    }

    public boolean exists(final String id) {
        return writeRepository.exists(id);
    }

    public long count() {
        return writeRepository.count();
    }

    public void delete(final String id) {
        writeRepository.delete(id);
    }

    public void delete(final WorkType workType) {
        final WorkTypeJpaModel workTypeModel = mapper.toModel(workType);
        writeRepository.delete(workTypeModel);
    }

    public void delete(final Iterable<? extends WorkType> entities) {
        final ArrayList<WorkTypeJpaModel> models = new ArrayList<>();

        entities.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.delete(models);
    }

    public void deleteAll() {
        writeRepository.deleteAll();
    }



}
