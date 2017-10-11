package io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure;

import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.jpa.PersonalTraitsJpaModel;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.jpa.PersonalTraitsJpaRepository;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.jpa.PersonalTraitsJpaSpecification;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.jpa.PersonalTraitsJpaMapper;
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

@Named("PersonalTraitsRepository")
@Transactional //Hibernate session depends on this.
public class PersonalTraitsRepository {

    private final PersonalTraitsJpaMapper mapper;
    private final PersonalTraitsJpaRepository writeRepository;
    private final JpaConversion jpaConversion;

    @Inject
    public PersonalTraitsRepository(final PersonalTraitsJpaMapper mapper,
                                    final PersonalTraitsJpaRepository writeRepository,
                                    final JpaConversion jpaConversion) {

        this.mapper = mapper;
        this.writeRepository = writeRepository;
        this.jpaConversion = jpaConversion;
    }

    public PersonalTraits findOne(final String id) {
        return mapper.toDomain(writeRepository.findOne(id));
    }

    public Page<PersonalTraits> findAll(final PageOptions pageOptions, final SortOptions sortOptions) {
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<PersonalTraitsJpaModel> modelPage = writeRepository.findAll(pageable);
        final List<PersonalTraits> list = modelPage.getContent()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    public PersonalTraits save(final PersonalTraits personalTraits) {
        final PersonalTraitsJpaModel personalTraitsModel = writeRepository.save(mapper.toModel(personalTraits));

        return mapper.toDomain(personalTraitsModel);
    }

    public List<PersonalTraits> findAll() {
        final List<PersonalTraitsJpaModel> list = writeRepository.findAll();

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<PersonalTraits> findAll(final SortOptions sortOptions) {
        final List<PersonalTraitsJpaModel> list = writeRepository.findAll(jpaConversion.fromSorting(sortOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<PersonalTraits> findAll(final Iterable<String> iterable) {
        final List<PersonalTraitsJpaModel> list = writeRepository.findAll(iterable);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<PersonalTraits> findAll(final FilterOptions filterOptions) {
        final List<PersonalTraitsJpaModel> list = writeRepository.findAll(jpaConversion.fromFilters(filterOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public Page<PersonalTraits> findAll(final FilterOptions filterOptions,
                                            final PageOptions pageOptions,
                                            final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<PersonalTraitsJpaModel> modelPage = writeRepository.findAll(specification, pageable);
        final List<PersonalTraits> list = modelPage.getContent()
            .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    @SuppressWarnings("unchecked")
    public List<PersonalTraits> findAll(final FilterOptions filterOptions, final SortOptions sortOptions) {
        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Sort sort = jpaConversion.fromSorting(sortOptions);
        final List<PersonalTraitsJpaModel> list = writeRepository.findAll(specification, sort);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public void flush() {
        writeRepository.flush();
    }

    public List<PersonalTraits> save(final Iterable<PersonalTraits> iterable) {
        final ArrayList<PersonalTraitsJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        final List<PersonalTraitsJpaModel> list = writeRepository.save(models);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }


    public PersonalTraits saveAndFlush(final PersonalTraits personalTraits) {
        final PersonalTraitsJpaModel personalTraitsModel = writeRepository.saveAndFlush(mapper.toModel(personalTraits));

        return mapper.toDomain(personalTraitsModel);
    }

    public void deleteInBatch(final Iterable<PersonalTraits> iterable) {
        final ArrayList<PersonalTraitsJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.deleteInBatch(models);
    }

    public void deleteAllInBatch() {
        writeRepository.deleteAllInBatch();
    }

    public PersonalTraits getOne(final String id) {
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

    public void delete(final PersonalTraits personalTraits) {
        final PersonalTraitsJpaModel personalTraitsModel = mapper.toModel(personalTraits);
        writeRepository.delete(personalTraitsModel);
    }

    public void delete(final Iterable<? extends PersonalTraits> entities) {
        final ArrayList<PersonalTraitsJpaModel> models = new ArrayList<>();

        entities.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.delete(models);
    }

    public void deleteAll() {
        writeRepository.deleteAll();
    }



// MANY-TO-ONE
    public void deleteTalent(final String id) {
    final PersonalTraitsJpaModel model = writeRepository.findOneWithTalent(id);
        model.setTalent(null);
        writeRepository.save(model);
    }

    public PersonalTraits findOneWithTalent(final String id) {
        return mapper.toDomain(writeRepository.findOneWithTalent(id));
    }

    @SuppressWarnings("unchecked")
    public Page<PersonalTraits> findByTalent(final String id,
                                                                        final FilterOptions filterOptions,
                                                                        final PageOptions pageOptions,
                                                                        final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Specification filteredSpecification = Specifications.where(PersonalTraitsJpaSpecification.byTalentSpecification(id)).and(specification);

        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<PersonalTraitsJpaModel> modelPage = (Page<PersonalTraitsJpaModel>) writeRepository.findAll(filteredSpecification, pageable);
        final List<PersonalTraits> list = modelPage.getContent()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }
// END: MANY-TO-ONE



}
