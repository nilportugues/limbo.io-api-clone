package io.bandit.limbo.limbo.modules.main.country.infrastructure;

import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaModel;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaRepository;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaSpecification;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaMapper;
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

@Named("CountryRepository")
@Transactional //Hibernate session depends on this.
public class CountryRepository {

    private final CountryJpaMapper mapper;
    private final CountryJpaRepository writeRepository;
    private final JpaConversion jpaConversion;

    @Inject
    public CountryRepository(final CountryJpaMapper mapper,
                                    final CountryJpaRepository writeRepository,
                                    final JpaConversion jpaConversion) {

        this.mapper = mapper;
        this.writeRepository = writeRepository;
        this.jpaConversion = jpaConversion;
    }

    public Country findOne(final String id) {
        return mapper.toDomain(writeRepository.findOne(id));
    }

    public Page<Country> findAll(final PageOptions pageOptions, final SortOptions sortOptions) {
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<CountryJpaModel> modelPage = writeRepository.findAll(pageable);
        final List<Country> list = modelPage.getContent()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    public Country save(final Country country) {
        final CountryJpaModel countryModel = writeRepository.save(mapper.toModel(country));

        return mapper.toDomain(countryModel);
    }

    public List<Country> findAll() {
        final List<CountryJpaModel> list = writeRepository.findAll();

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<Country> findAll(final SortOptions sortOptions) {
        final List<CountryJpaModel> list = writeRepository.findAll(jpaConversion.fromSorting(sortOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<Country> findAll(final Iterable<String> iterable) {
        final List<CountryJpaModel> list = writeRepository.findAll(iterable);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<Country> findAll(final FilterOptions filterOptions) {
        final List<CountryJpaModel> list = writeRepository.findAll(jpaConversion.fromFilters(filterOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public Page<Country> findAll(final FilterOptions filterOptions,
                                            final PageOptions pageOptions,
                                            final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<CountryJpaModel> modelPage = writeRepository.findAll(specification, pageable);
        final List<Country> list = modelPage.getContent()
            .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    @SuppressWarnings("unchecked")
    public List<Country> findAll(final FilterOptions filterOptions, final SortOptions sortOptions) {
        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Sort sort = jpaConversion.fromSorting(sortOptions);
        final List<CountryJpaModel> list = writeRepository.findAll(specification, sort);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public void flush() {
        writeRepository.flush();
    }

    public List<Country> save(final Iterable<Country> iterable) {
        final ArrayList<CountryJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        final List<CountryJpaModel> list = writeRepository.save(models);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }


    public Country saveAndFlush(final Country country) {
        final CountryJpaModel countryModel = writeRepository.saveAndFlush(mapper.toModel(country));

        return mapper.toDomain(countryModel);
    }

    public void deleteInBatch(final Iterable<Country> iterable) {
        final ArrayList<CountryJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.deleteInBatch(models);
    }

    public void deleteAllInBatch() {
        writeRepository.deleteAllInBatch();
    }

    public Country getOne(final String id) {
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

    public void delete(final Country country) {
        final CountryJpaModel countryModel = mapper.toModel(country);
        writeRepository.delete(countryModel);
    }

    public void delete(final Iterable<? extends Country> entities) {
        final ArrayList<CountryJpaModel> models = new ArrayList<>();

        entities.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.delete(models);
    }

    public void deleteAll() {
        writeRepository.deleteAll();
    }


// ONE-TO-MANY

    public Country findOneWithCities(final String id) {
        return mapper.toDomain(writeRepository.findOneWithCities(id));
    }

    /**
     * Find the Country that happen to have an instance of City with matching id.
     *
     * @param cityId City identifier value.
     */
    @SuppressWarnings("unchecked")
    public Country findCountryByCity(final String cityId) {

        final Specification<CountryJpaModel> specification = CountryJpaSpecification.byCitySpecification(cityId);

        return mapper.toDomain((CountryJpaModel) writeRepository.findOne(specification));
    }

    /**
     * Remove the Country that happen to have an instance of City with matching id.
     *
     * @param cityId City identifier value.
     */
    @SuppressWarnings("unchecked")
    public void deleteCountryByCity(final String cityId) {
        final Specification<CountryJpaModel> specification = CountryJpaSpecification.byCitySpecification(cityId);

        final CountryJpaModel model = (CountryJpaModel) writeRepository.findOne(specification);

        if (Optional.ofNullable(model).isPresent()) {
            writeRepository.delete(model);
        }
    }

    /**
     * Remove all Cities from instance Country of matching id.
     *
     * @param countryId Country identifier value.
     */
    @SuppressWarnings("unchecked")
    public void deleteAllCities(final String countryId) {
        final CountryJpaModel countryModel = writeRepository.findOneWithCities(countryId);

        countryModel.setCities(null);
        writeRepository.save(countryModel);
    }

    /**
     * Remove all the listed Cities from instance Country of matching id.
     *
     * @param countryId Country identifier value.
     * @param cityIds List of City ids.
     */
    @SuppressWarnings("unchecked")
    public void deleteAllCities(final String countryId, final Iterable<String> cityIds) {
        final CountryJpaModel countryModel = writeRepository.findOneWithCities(countryId);

        final List<String> ids = new ArrayList<>();
        cityIds.forEach(ids::add);

        countryModel
            .getCities()
            .stream()
            .filter(cityModel -> ids.contains(cityModel.getId()))
            .forEach(cityModel -> cityModel.setCountry(null));

        writeRepository.save(countryModel);
    }

// END: ONE-TO-MANY




}
