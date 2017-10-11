package io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure;

import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.jpa.TalentProfileJpaModel;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.jpa.TalentProfileJpaRepository;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.jpa.TalentProfileJpaSpecification;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.jpa.TalentProfileJpaMapper;
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

@Named("TalentProfileRepository")
@Transactional //Hibernate session depends on this.
public class TalentProfileRepository {

    private final TalentProfileJpaMapper mapper;
    private final TalentProfileJpaRepository writeRepository;
    private final JpaConversion jpaConversion;

    @Inject
    public TalentProfileRepository(final TalentProfileJpaMapper mapper,
                                    final TalentProfileJpaRepository writeRepository,
                                    final JpaConversion jpaConversion) {

        this.mapper = mapper;
        this.writeRepository = writeRepository;
        this.jpaConversion = jpaConversion;
    }

    public TalentProfile findOne(final String id) {
        return mapper.toDomain(writeRepository.findOne(id));
    }

    public Page<TalentProfile> findAll(final PageOptions pageOptions, final SortOptions sortOptions) {
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<TalentProfileJpaModel> modelPage = writeRepository.findAll(pageable);
        final List<TalentProfile> list = modelPage.getContent()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    public TalentProfile save(final TalentProfile talentProfile) {
        final TalentProfileJpaModel talentProfileModel = writeRepository.save(mapper.toModel(talentProfile));

        return mapper.toDomain(talentProfileModel);
    }

    public List<TalentProfile> findAll() {
        final List<TalentProfileJpaModel> list = writeRepository.findAll();

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<TalentProfile> findAll(final SortOptions sortOptions) {
        final List<TalentProfileJpaModel> list = writeRepository.findAll(jpaConversion.fromSorting(sortOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<TalentProfile> findAll(final Iterable<String> iterable) {
        final List<TalentProfileJpaModel> list = writeRepository.findAll(iterable);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<TalentProfile> findAll(final FilterOptions filterOptions) {
        final List<TalentProfileJpaModel> list = writeRepository.findAll(jpaConversion.fromFilters(filterOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public Page<TalentProfile> findAll(final FilterOptions filterOptions,
                                            final PageOptions pageOptions,
                                            final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<TalentProfileJpaModel> modelPage = writeRepository.findAll(specification, pageable);
        final List<TalentProfile> list = modelPage.getContent()
            .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    @SuppressWarnings("unchecked")
    public List<TalentProfile> findAll(final FilterOptions filterOptions, final SortOptions sortOptions) {
        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Sort sort = jpaConversion.fromSorting(sortOptions);
        final List<TalentProfileJpaModel> list = writeRepository.findAll(specification, sort);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public void flush() {
        writeRepository.flush();
    }

    public List<TalentProfile> save(final Iterable<TalentProfile> iterable) {
        final ArrayList<TalentProfileJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        final List<TalentProfileJpaModel> list = writeRepository.save(models);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }


    public TalentProfile saveAndFlush(final TalentProfile talentProfile) {
        final TalentProfileJpaModel talentProfileModel = writeRepository.saveAndFlush(mapper.toModel(talentProfile));

        return mapper.toDomain(talentProfileModel);
    }

    public void deleteInBatch(final Iterable<TalentProfile> iterable) {
        final ArrayList<TalentProfileJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.deleteInBatch(models);
    }

    public void deleteAllInBatch() {
        writeRepository.deleteAllInBatch();
    }

    public TalentProfile getOne(final String id) {
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

    public void delete(final TalentProfile talentProfile) {
        final TalentProfileJpaModel talentProfileModel = mapper.toModel(talentProfile);
        writeRepository.delete(talentProfileModel);
    }

    public void delete(final Iterable<? extends TalentProfile> entities) {
        final ArrayList<TalentProfileJpaModel> models = new ArrayList<>();

        entities.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.delete(models);
    }

    public void deleteAll() {
        writeRepository.deleteAll();
    }



}
