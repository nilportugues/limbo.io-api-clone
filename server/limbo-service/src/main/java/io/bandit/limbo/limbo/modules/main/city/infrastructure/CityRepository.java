package io.bandit.limbo.limbo.modules.main.city.infrastructure;

import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaModel;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaRepository;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaSpecification;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaMapper;
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

@Named("CityRepository")
@Transactional //Hibernate session depends on this.
public class CityRepository {

    private final CityJpaMapper mapper;
    private final CityJpaRepository writeRepository;
    private final JpaConversion jpaConversion;

    @Inject
    public CityRepository(final CityJpaMapper mapper,
                                    final CityJpaRepository writeRepository,
                                    final JpaConversion jpaConversion) {

        this.mapper = mapper;
        this.writeRepository = writeRepository;
        this.jpaConversion = jpaConversion;
    }

    public City findOne(final String id) {
        return mapper.toDomain(writeRepository.findOne(id));
    }

    public Page<City> findAll(final PageOptions pageOptions, final SortOptions sortOptions) {
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<CityJpaModel> modelPage = writeRepository.findAll(pageable);
        final List<City> list = modelPage.getContent()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    public City save(final City city) {
        final CityJpaModel cityModel = writeRepository.save(mapper.toModel(city));

        return mapper.toDomain(cityModel);
    }

    public List<City> findAll() {
        final List<CityJpaModel> list = writeRepository.findAll();

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<City> findAll(final SortOptions sortOptions) {
        final List<CityJpaModel> list = writeRepository.findAll(jpaConversion.fromSorting(sortOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<City> findAll(final Iterable<String> iterable) {
        final List<CityJpaModel> list = writeRepository.findAll(iterable);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<City> findAll(final FilterOptions filterOptions) {
        final List<CityJpaModel> list = writeRepository.findAll(jpaConversion.fromFilters(filterOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public Page<City> findAll(final FilterOptions filterOptions,
                                            final PageOptions pageOptions,
                                            final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<CityJpaModel> modelPage = writeRepository.findAll(specification, pageable);
        final List<City> list = modelPage.getContent()
            .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    @SuppressWarnings("unchecked")
    public List<City> findAll(final FilterOptions filterOptions, final SortOptions sortOptions) {
        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Sort sort = jpaConversion.fromSorting(sortOptions);
        final List<CityJpaModel> list = writeRepository.findAll(specification, sort);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public void flush() {
        writeRepository.flush();
    }

    public List<City> save(final Iterable<City> iterable) {
        final ArrayList<CityJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        final List<CityJpaModel> list = writeRepository.save(models);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }


    public City saveAndFlush(final City city) {
        final CityJpaModel cityModel = writeRepository.saveAndFlush(mapper.toModel(city));

        return mapper.toDomain(cityModel);
    }

    public void deleteInBatch(final Iterable<City> iterable) {
        final ArrayList<CityJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.deleteInBatch(models);
    }

    public void deleteAllInBatch() {
        writeRepository.deleteAllInBatch();
    }

    public City getOne(final String id) {
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

    public void delete(final City city) {
        final CityJpaModel cityModel = mapper.toModel(city);
        writeRepository.delete(cityModel);
    }

    public void delete(final Iterable<? extends City> entities) {
        final ArrayList<CityJpaModel> models = new ArrayList<>();

        entities.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.delete(models);
    }

    public void deleteAll() {
        writeRepository.deleteAll();
    }



// MANY-TO-ONE
    public void deleteCountry(final String id) {
    final CityJpaModel model = writeRepository.findOneWithCountry(id);
        model.setCountry(null);
        writeRepository.save(model);
    }

    public City findOneWithCountry(final String id) {
        return mapper.toDomain(writeRepository.findOneWithCountry(id));
    }

    @SuppressWarnings("unchecked")
    public Page<City> findByCountry(final String id,
                                                                        final FilterOptions filterOptions,
                                                                        final PageOptions pageOptions,
                                                                        final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Specification filteredSpecification = Specifications.where(CityJpaSpecification.byCountrySpecification(id)).and(specification);

        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<CityJpaModel> modelPage = (Page<CityJpaModel>) writeRepository.findAll(filteredSpecification, pageable);
        final List<City> list = modelPage.getContent()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }
// END: MANY-TO-ONE



}
