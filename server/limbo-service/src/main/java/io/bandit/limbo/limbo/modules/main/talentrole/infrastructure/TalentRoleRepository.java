package io.bandit.limbo.limbo.modules.main.talentrole.infrastructure;

import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.jpa.TalentRoleJpaModel;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.jpa.TalentRoleJpaRepository;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.jpa.TalentRoleJpaSpecification;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.jpa.TalentRoleJpaMapper;
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

@Named("TalentRoleRepository")
@Transactional //Hibernate session depends on this.
public class TalentRoleRepository {

    private final TalentRoleJpaMapper mapper;
    private final TalentRoleJpaRepository writeRepository;
    private final JpaConversion jpaConversion;

    @Inject
    public TalentRoleRepository(final TalentRoleJpaMapper mapper,
                                    final TalentRoleJpaRepository writeRepository,
                                    final JpaConversion jpaConversion) {

        this.mapper = mapper;
        this.writeRepository = writeRepository;
        this.jpaConversion = jpaConversion;
    }

    public TalentRole findOne(final String id) {
        return mapper.toDomain(writeRepository.findOne(id));
    }

    public Page<TalentRole> findAll(final PageOptions pageOptions, final SortOptions sortOptions) {
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<TalentRoleJpaModel> modelPage = writeRepository.findAll(pageable);
        final List<TalentRole> list = modelPage.getContent()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    public TalentRole save(final TalentRole talentRole) {
        final TalentRoleJpaModel talentRoleModel = writeRepository.save(mapper.toModel(talentRole));

        return mapper.toDomain(talentRoleModel);
    }

    public List<TalentRole> findAll() {
        final List<TalentRoleJpaModel> list = writeRepository.findAll();

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<TalentRole> findAll(final SortOptions sortOptions) {
        final List<TalentRoleJpaModel> list = writeRepository.findAll(jpaConversion.fromSorting(sortOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<TalentRole> findAll(final Iterable<String> iterable) {
        final List<TalentRoleJpaModel> list = writeRepository.findAll(iterable);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<TalentRole> findAll(final FilterOptions filterOptions) {
        final List<TalentRoleJpaModel> list = writeRepository.findAll(jpaConversion.fromFilters(filterOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public Page<TalentRole> findAll(final FilterOptions filterOptions,
                                            final PageOptions pageOptions,
                                            final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<TalentRoleJpaModel> modelPage = writeRepository.findAll(specification, pageable);
        final List<TalentRole> list = modelPage.getContent()
            .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    @SuppressWarnings("unchecked")
    public List<TalentRole> findAll(final FilterOptions filterOptions, final SortOptions sortOptions) {
        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Sort sort = jpaConversion.fromSorting(sortOptions);
        final List<TalentRoleJpaModel> list = writeRepository.findAll(specification, sort);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public void flush() {
        writeRepository.flush();
    }

    public List<TalentRole> save(final Iterable<TalentRole> iterable) {
        final ArrayList<TalentRoleJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        final List<TalentRoleJpaModel> list = writeRepository.save(models);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }


    public TalentRole saveAndFlush(final TalentRole talentRole) {
        final TalentRoleJpaModel talentRoleModel = writeRepository.saveAndFlush(mapper.toModel(talentRole));

        return mapper.toDomain(talentRoleModel);
    }

    public void deleteInBatch(final Iterable<TalentRole> iterable) {
        final ArrayList<TalentRoleJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.deleteInBatch(models);
    }

    public void deleteAllInBatch() {
        writeRepository.deleteAllInBatch();
    }

    public TalentRole getOne(final String id) {
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

    public void delete(final TalentRole talentRole) {
        final TalentRoleJpaModel talentRoleModel = mapper.toModel(talentRole);
        writeRepository.delete(talentRoleModel);
    }

    public void delete(final Iterable<? extends TalentRole> entities) {
        final ArrayList<TalentRoleJpaModel> models = new ArrayList<>();

        entities.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.delete(models);
    }

    public void deleteAll() {
        writeRepository.deleteAll();
    }



}
