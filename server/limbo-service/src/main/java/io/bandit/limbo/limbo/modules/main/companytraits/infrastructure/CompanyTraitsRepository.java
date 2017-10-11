package io.bandit.limbo.limbo.modules.main.companytraits.infrastructure;

import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.jpa.CompanyTraitsJpaModel;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.jpa.CompanyTraitsJpaRepository;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.jpa.CompanyTraitsJpaSpecification;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.jpa.CompanyTraitsJpaMapper;
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

@Named("CompanyTraitsRepository")
@Transactional //Hibernate session depends on this.
public class CompanyTraitsRepository {

    private final CompanyTraitsJpaMapper mapper;
    private final CompanyTraitsJpaRepository writeRepository;
    private final JpaConversion jpaConversion;

    @Inject
    public CompanyTraitsRepository(final CompanyTraitsJpaMapper mapper,
                                    final CompanyTraitsJpaRepository writeRepository,
                                    final JpaConversion jpaConversion) {

        this.mapper = mapper;
        this.writeRepository = writeRepository;
        this.jpaConversion = jpaConversion;
    }

    public CompanyTraits findOne(final String id) {
        return mapper.toDomain(writeRepository.findOne(id));
    }

    public Page<CompanyTraits> findAll(final PageOptions pageOptions, final SortOptions sortOptions) {
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<CompanyTraitsJpaModel> modelPage = writeRepository.findAll(pageable);
        final List<CompanyTraits> list = modelPage.getContent()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    public CompanyTraits save(final CompanyTraits companyTraits) {
        final CompanyTraitsJpaModel companyTraitsModel = writeRepository.save(mapper.toModel(companyTraits));

        return mapper.toDomain(companyTraitsModel);
    }

    public List<CompanyTraits> findAll() {
        final List<CompanyTraitsJpaModel> list = writeRepository.findAll();

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<CompanyTraits> findAll(final SortOptions sortOptions) {
        final List<CompanyTraitsJpaModel> list = writeRepository.findAll(jpaConversion.fromSorting(sortOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<CompanyTraits> findAll(final Iterable<String> iterable) {
        final List<CompanyTraitsJpaModel> list = writeRepository.findAll(iterable);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<CompanyTraits> findAll(final FilterOptions filterOptions) {
        final List<CompanyTraitsJpaModel> list = writeRepository.findAll(jpaConversion.fromFilters(filterOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public Page<CompanyTraits> findAll(final FilterOptions filterOptions,
                                            final PageOptions pageOptions,
                                            final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<CompanyTraitsJpaModel> modelPage = writeRepository.findAll(specification, pageable);
        final List<CompanyTraits> list = modelPage.getContent()
            .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    @SuppressWarnings("unchecked")
    public List<CompanyTraits> findAll(final FilterOptions filterOptions, final SortOptions sortOptions) {
        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Sort sort = jpaConversion.fromSorting(sortOptions);
        final List<CompanyTraitsJpaModel> list = writeRepository.findAll(specification, sort);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public void flush() {
        writeRepository.flush();
    }

    public List<CompanyTraits> save(final Iterable<CompanyTraits> iterable) {
        final ArrayList<CompanyTraitsJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        final List<CompanyTraitsJpaModel> list = writeRepository.save(models);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }


    public CompanyTraits saveAndFlush(final CompanyTraits companyTraits) {
        final CompanyTraitsJpaModel companyTraitsModel = writeRepository.saveAndFlush(mapper.toModel(companyTraits));

        return mapper.toDomain(companyTraitsModel);
    }

    public void deleteInBatch(final Iterable<CompanyTraits> iterable) {
        final ArrayList<CompanyTraitsJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.deleteInBatch(models);
    }

    public void deleteAllInBatch() {
        writeRepository.deleteAllInBatch();
    }

    public CompanyTraits getOne(final String id) {
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

    public void delete(final CompanyTraits companyTraits) {
        final CompanyTraitsJpaModel companyTraitsModel = mapper.toModel(companyTraits);
        writeRepository.delete(companyTraitsModel);
    }

    public void delete(final Iterable<? extends CompanyTraits> entities) {
        final ArrayList<CompanyTraitsJpaModel> models = new ArrayList<>();

        entities.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.delete(models);
    }

    public void deleteAll() {
        writeRepository.deleteAll();
    }



// MANY-TO-ONE
    public void deleteTalent(final String id) {
    final CompanyTraitsJpaModel model = writeRepository.findOneWithTalent(id);
        model.setTalent(null);
        writeRepository.save(model);
    }

    public CompanyTraits findOneWithTalent(final String id) {
        return mapper.toDomain(writeRepository.findOneWithTalent(id));
    }

    @SuppressWarnings("unchecked")
    public Page<CompanyTraits> findByTalent(final String id,
                                                                        final FilterOptions filterOptions,
                                                                        final PageOptions pageOptions,
                                                                        final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Specification filteredSpecification = Specifications.where(CompanyTraitsJpaSpecification.byTalentSpecification(id)).and(specification);

        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<CompanyTraitsJpaModel> modelPage = (Page<CompanyTraitsJpaModel>) writeRepository.findAll(filteredSpecification, pageable);
        final List<CompanyTraits> list = modelPage.getContent()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }
// END: MANY-TO-ONE



}
