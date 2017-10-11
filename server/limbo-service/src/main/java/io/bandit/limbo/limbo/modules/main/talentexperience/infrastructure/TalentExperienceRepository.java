package io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure;

import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.jpa.TalentExperienceJpaModel;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.jpa.TalentExperienceJpaRepository;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.jpa.TalentExperienceJpaSpecification;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.jpa.TalentExperienceJpaMapper;
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

@Named("TalentExperienceRepository")
@Transactional //Hibernate session depends on this.
public class TalentExperienceRepository {

    private final TalentExperienceJpaMapper mapper;
    private final TalentExperienceJpaRepository writeRepository;
    private final JpaConversion jpaConversion;

    @Inject
    public TalentExperienceRepository(final TalentExperienceJpaMapper mapper,
                                    final TalentExperienceJpaRepository writeRepository,
                                    final JpaConversion jpaConversion) {

        this.mapper = mapper;
        this.writeRepository = writeRepository;
        this.jpaConversion = jpaConversion;
    }

    public TalentExperience findOne(final String id) {
        return mapper.toDomain(writeRepository.findOne(id));
    }

    public Page<TalentExperience> findAll(final PageOptions pageOptions, final SortOptions sortOptions) {
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<TalentExperienceJpaModel> modelPage = writeRepository.findAll(pageable);
        final List<TalentExperience> list = modelPage.getContent()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    public TalentExperience save(final TalentExperience talentExperience) {
        final TalentExperienceJpaModel talentExperienceModel = writeRepository.save(mapper.toModel(talentExperience));

        return mapper.toDomain(talentExperienceModel);
    }

    public List<TalentExperience> findAll() {
        final List<TalentExperienceJpaModel> list = writeRepository.findAll();

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<TalentExperience> findAll(final SortOptions sortOptions) {
        final List<TalentExperienceJpaModel> list = writeRepository.findAll(jpaConversion.fromSorting(sortOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<TalentExperience> findAll(final Iterable<String> iterable) {
        final List<TalentExperienceJpaModel> list = writeRepository.findAll(iterable);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<TalentExperience> findAll(final FilterOptions filterOptions) {
        final List<TalentExperienceJpaModel> list = writeRepository.findAll(jpaConversion.fromFilters(filterOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public Page<TalentExperience> findAll(final FilterOptions filterOptions,
                                            final PageOptions pageOptions,
                                            final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<TalentExperienceJpaModel> modelPage = writeRepository.findAll(specification, pageable);
        final List<TalentExperience> list = modelPage.getContent()
            .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    @SuppressWarnings("unchecked")
    public List<TalentExperience> findAll(final FilterOptions filterOptions, final SortOptions sortOptions) {
        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Sort sort = jpaConversion.fromSorting(sortOptions);
        final List<TalentExperienceJpaModel> list = writeRepository.findAll(specification, sort);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public void flush() {
        writeRepository.flush();
    }

    public List<TalentExperience> save(final Iterable<TalentExperience> iterable) {
        final ArrayList<TalentExperienceJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        final List<TalentExperienceJpaModel> list = writeRepository.save(models);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }


    public TalentExperience saveAndFlush(final TalentExperience talentExperience) {
        final TalentExperienceJpaModel talentExperienceModel = writeRepository.saveAndFlush(mapper.toModel(talentExperience));

        return mapper.toDomain(talentExperienceModel);
    }

    public void deleteInBatch(final Iterable<TalentExperience> iterable) {
        final ArrayList<TalentExperienceJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.deleteInBatch(models);
    }

    public void deleteAllInBatch() {
        writeRepository.deleteAllInBatch();
    }

    public TalentExperience getOne(final String id) {
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

    public void delete(final TalentExperience talentExperience) {
        final TalentExperienceJpaModel talentExperienceModel = mapper.toModel(talentExperience);
        writeRepository.delete(talentExperienceModel);
    }

    public void delete(final Iterable<? extends TalentExperience> entities) {
        final ArrayList<TalentExperienceJpaModel> models = new ArrayList<>();

        entities.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.delete(models);
    }

    public void deleteAll() {
        writeRepository.deleteAll();
    }



}
