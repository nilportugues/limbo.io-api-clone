package io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure;

import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.jpa.NotableProjectsJpaModel;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.jpa.NotableProjectsJpaRepository;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.jpa.NotableProjectsJpaSpecification;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.jpa.NotableProjectsJpaMapper;
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

@Named("NotableProjectsRepository")
@Transactional //Hibernate session depends on this.
public class NotableProjectsRepository {

    private final NotableProjectsJpaMapper mapper;
    private final NotableProjectsJpaRepository writeRepository;
    private final JpaConversion jpaConversion;

    @Inject
    public NotableProjectsRepository(final NotableProjectsJpaMapper mapper,
                                    final NotableProjectsJpaRepository writeRepository,
                                    final JpaConversion jpaConversion) {

        this.mapper = mapper;
        this.writeRepository = writeRepository;
        this.jpaConversion = jpaConversion;
    }

    public NotableProjects findOne(final String id) {
        return mapper.toDomain(writeRepository.findOne(id));
    }

    public Page<NotableProjects> findAll(final PageOptions pageOptions, final SortOptions sortOptions) {
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<NotableProjectsJpaModel> modelPage = writeRepository.findAll(pageable);
        final List<NotableProjects> list = modelPage.getContent()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    public NotableProjects save(final NotableProjects notableProjects) {
        final NotableProjectsJpaModel notableProjectsModel = writeRepository.save(mapper.toModel(notableProjects));

        return mapper.toDomain(notableProjectsModel);
    }

    public List<NotableProjects> findAll() {
        final List<NotableProjectsJpaModel> list = writeRepository.findAll();

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<NotableProjects> findAll(final SortOptions sortOptions) {
        final List<NotableProjectsJpaModel> list = writeRepository.findAll(jpaConversion.fromSorting(sortOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<NotableProjects> findAll(final Iterable<String> iterable) {
        final List<NotableProjectsJpaModel> list = writeRepository.findAll(iterable);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<NotableProjects> findAll(final FilterOptions filterOptions) {
        final List<NotableProjectsJpaModel> list = writeRepository.findAll(jpaConversion.fromFilters(filterOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public Page<NotableProjects> findAll(final FilterOptions filterOptions,
                                            final PageOptions pageOptions,
                                            final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<NotableProjectsJpaModel> modelPage = writeRepository.findAll(specification, pageable);
        final List<NotableProjects> list = modelPage.getContent()
            .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    @SuppressWarnings("unchecked")
    public List<NotableProjects> findAll(final FilterOptions filterOptions, final SortOptions sortOptions) {
        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Sort sort = jpaConversion.fromSorting(sortOptions);
        final List<NotableProjectsJpaModel> list = writeRepository.findAll(specification, sort);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public void flush() {
        writeRepository.flush();
    }

    public List<NotableProjects> save(final Iterable<NotableProjects> iterable) {
        final ArrayList<NotableProjectsJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        final List<NotableProjectsJpaModel> list = writeRepository.save(models);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }


    public NotableProjects saveAndFlush(final NotableProjects notableProjects) {
        final NotableProjectsJpaModel notableProjectsModel = writeRepository.saveAndFlush(mapper.toModel(notableProjects));

        return mapper.toDomain(notableProjectsModel);
    }

    public void deleteInBatch(final Iterable<NotableProjects> iterable) {
        final ArrayList<NotableProjectsJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.deleteInBatch(models);
    }

    public void deleteAllInBatch() {
        writeRepository.deleteAllInBatch();
    }

    public NotableProjects getOne(final String id) {
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

    public void delete(final NotableProjects notableProjects) {
        final NotableProjectsJpaModel notableProjectsModel = mapper.toModel(notableProjects);
        writeRepository.delete(notableProjectsModel);
    }

    public void delete(final Iterable<? extends NotableProjects> entities) {
        final ArrayList<NotableProjectsJpaModel> models = new ArrayList<>();

        entities.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.delete(models);
    }

    public void deleteAll() {
        writeRepository.deleteAll();
    }



// MANY-TO-ONE
    public void deleteTalent(final String id) {
    final NotableProjectsJpaModel model = writeRepository.findOneWithTalent(id);
        model.setTalent(null);
        writeRepository.save(model);
    }

    public NotableProjects findOneWithTalent(final String id) {
        return mapper.toDomain(writeRepository.findOneWithTalent(id));
    }

    @SuppressWarnings("unchecked")
    public Page<NotableProjects> findByTalent(final String id,
                                                                        final FilterOptions filterOptions,
                                                                        final PageOptions pageOptions,
                                                                        final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Specification filteredSpecification = Specifications.where(NotableProjectsJpaSpecification.byTalentSpecification(id)).and(specification);

        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<NotableProjectsJpaModel> modelPage = (Page<NotableProjectsJpaModel>) writeRepository.findAll(filteredSpecification, pageable);
        final List<NotableProjects> list = modelPage.getContent()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }
// END: MANY-TO-ONE



}
