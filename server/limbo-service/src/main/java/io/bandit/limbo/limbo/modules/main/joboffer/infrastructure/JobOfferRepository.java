package io.bandit.limbo.limbo.modules.main.joboffer.infrastructure;

import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.jpa.JobOfferJpaModel;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.jpa.JobOfferJpaRepository;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.jpa.JobOfferJpaSpecification;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.jpa.JobOfferJpaMapper;
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

@Named("JobOfferRepository")
@Transactional //Hibernate session depends on this.
public class JobOfferRepository {

    private final JobOfferJpaMapper mapper;
    private final JobOfferJpaRepository writeRepository;
    private final JpaConversion jpaConversion;

    @Inject
    public JobOfferRepository(final JobOfferJpaMapper mapper,
                                    final JobOfferJpaRepository writeRepository,
                                    final JpaConversion jpaConversion) {

        this.mapper = mapper;
        this.writeRepository = writeRepository;
        this.jpaConversion = jpaConversion;
    }

    public JobOffer findOne(final String id) {
        return mapper.toDomain(writeRepository.findOne(id));
    }

    public Page<JobOffer> findAll(final PageOptions pageOptions, final SortOptions sortOptions) {
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<JobOfferJpaModel> modelPage = writeRepository.findAll(pageable);
        final List<JobOffer> list = modelPage.getContent()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    public JobOffer save(final JobOffer jobOffer) {
        final JobOfferJpaModel jobOfferModel = writeRepository.save(mapper.toModel(jobOffer));

        return mapper.toDomain(jobOfferModel);
    }

    public List<JobOffer> findAll() {
        final List<JobOfferJpaModel> list = writeRepository.findAll();

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<JobOffer> findAll(final SortOptions sortOptions) {
        final List<JobOfferJpaModel> list = writeRepository.findAll(jpaConversion.fromSorting(sortOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<JobOffer> findAll(final Iterable<String> iterable) {
        final List<JobOfferJpaModel> list = writeRepository.findAll(iterable);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<JobOffer> findAll(final FilterOptions filterOptions) {
        final List<JobOfferJpaModel> list = writeRepository.findAll(jpaConversion.fromFilters(filterOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public Page<JobOffer> findAll(final FilterOptions filterOptions,
                                            final PageOptions pageOptions,
                                            final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<JobOfferJpaModel> modelPage = writeRepository.findAll(specification, pageable);
        final List<JobOffer> list = modelPage.getContent()
            .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    @SuppressWarnings("unchecked")
    public List<JobOffer> findAll(final FilterOptions filterOptions, final SortOptions sortOptions) {
        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Sort sort = jpaConversion.fromSorting(sortOptions);
        final List<JobOfferJpaModel> list = writeRepository.findAll(specification, sort);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public void flush() {
        writeRepository.flush();
    }

    public List<JobOffer> save(final Iterable<JobOffer> iterable) {
        final ArrayList<JobOfferJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        final List<JobOfferJpaModel> list = writeRepository.save(models);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }


    public JobOffer saveAndFlush(final JobOffer jobOffer) {
        final JobOfferJpaModel jobOfferModel = writeRepository.saveAndFlush(mapper.toModel(jobOffer));

        return mapper.toDomain(jobOfferModel);
    }

    public void deleteInBatch(final Iterable<JobOffer> iterable) {
        final ArrayList<JobOfferJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.deleteInBatch(models);
    }

    public void deleteAllInBatch() {
        writeRepository.deleteAllInBatch();
    }

    public JobOffer getOne(final String id) {
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

    public void delete(final JobOffer jobOffer) {
        final JobOfferJpaModel jobOfferModel = mapper.toModel(jobOffer);
        writeRepository.delete(jobOfferModel);
    }

    public void delete(final Iterable<? extends JobOffer> entities) {
        final ArrayList<JobOfferJpaModel> models = new ArrayList<>();

        entities.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.delete(models);
    }

    public void deleteAll() {
        writeRepository.deleteAll();
    }



// MANY-TO-ONE
    public void deleteTalent(final String id) {
    final JobOfferJpaModel model = writeRepository.findOneWithTalent(id);
        model.setTalent(null);
        writeRepository.save(model);
    }

    public JobOffer findOneWithTalent(final String id) {
        return mapper.toDomain(writeRepository.findOneWithTalent(id));
    }

    @SuppressWarnings("unchecked")
    public Page<JobOffer> findByTalent(final String id,
                                                                        final FilterOptions filterOptions,
                                                                        final PageOptions pageOptions,
                                                                        final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Specification filteredSpecification = Specifications.where(JobOfferJpaSpecification.byTalentSpecification(id)).and(specification);

        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<JobOfferJpaModel> modelPage = (Page<JobOfferJpaModel>) writeRepository.findAll(filteredSpecification, pageable);
        final List<JobOffer> list = modelPage.getContent()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }
// END: MANY-TO-ONE



}
