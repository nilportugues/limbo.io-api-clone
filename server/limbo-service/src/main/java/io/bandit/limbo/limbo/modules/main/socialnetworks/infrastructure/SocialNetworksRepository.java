package io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure;

import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.jpa.SocialNetworksJpaModel;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.jpa.SocialNetworksJpaRepository;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.jpa.SocialNetworksJpaSpecification;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.jpa.SocialNetworksJpaMapper;
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

@Named("SocialNetworksRepository")
@Transactional //Hibernate session depends on this.
public class SocialNetworksRepository {

    private final SocialNetworksJpaMapper mapper;
    private final SocialNetworksJpaRepository writeRepository;
    private final JpaConversion jpaConversion;

    @Inject
    public SocialNetworksRepository(final SocialNetworksJpaMapper mapper,
                                    final SocialNetworksJpaRepository writeRepository,
                                    final JpaConversion jpaConversion) {

        this.mapper = mapper;
        this.writeRepository = writeRepository;
        this.jpaConversion = jpaConversion;
    }

    public SocialNetworks findOne(final String id) {
        return mapper.toDomain(writeRepository.findOne(id));
    }

    public Page<SocialNetworks> findAll(final PageOptions pageOptions, final SortOptions sortOptions) {
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<SocialNetworksJpaModel> modelPage = writeRepository.findAll(pageable);
        final List<SocialNetworks> list = modelPage.getContent()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    public SocialNetworks save(final SocialNetworks socialNetworks) {
        final SocialNetworksJpaModel socialNetworksModel = writeRepository.save(mapper.toModel(socialNetworks));

        return mapper.toDomain(socialNetworksModel);
    }

    public List<SocialNetworks> findAll() {
        final List<SocialNetworksJpaModel> list = writeRepository.findAll();

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<SocialNetworks> findAll(final SortOptions sortOptions) {
        final List<SocialNetworksJpaModel> list = writeRepository.findAll(jpaConversion.fromSorting(sortOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<SocialNetworks> findAll(final Iterable<String> iterable) {
        final List<SocialNetworksJpaModel> list = writeRepository.findAll(iterable);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<SocialNetworks> findAll(final FilterOptions filterOptions) {
        final List<SocialNetworksJpaModel> list = writeRepository.findAll(jpaConversion.fromFilters(filterOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public Page<SocialNetworks> findAll(final FilterOptions filterOptions,
                                            final PageOptions pageOptions,
                                            final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<SocialNetworksJpaModel> modelPage = writeRepository.findAll(specification, pageable);
        final List<SocialNetworks> list = modelPage.getContent()
            .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    @SuppressWarnings("unchecked")
    public List<SocialNetworks> findAll(final FilterOptions filterOptions, final SortOptions sortOptions) {
        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Sort sort = jpaConversion.fromSorting(sortOptions);
        final List<SocialNetworksJpaModel> list = writeRepository.findAll(specification, sort);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public void flush() {
        writeRepository.flush();
    }

    public List<SocialNetworks> save(final Iterable<SocialNetworks> iterable) {
        final ArrayList<SocialNetworksJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        final List<SocialNetworksJpaModel> list = writeRepository.save(models);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }


    public SocialNetworks saveAndFlush(final SocialNetworks socialNetworks) {
        final SocialNetworksJpaModel socialNetworksModel = writeRepository.saveAndFlush(mapper.toModel(socialNetworks));

        return mapper.toDomain(socialNetworksModel);
    }

    public void deleteInBatch(final Iterable<SocialNetworks> iterable) {
        final ArrayList<SocialNetworksJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.deleteInBatch(models);
    }

    public void deleteAllInBatch() {
        writeRepository.deleteAllInBatch();
    }

    public SocialNetworks getOne(final String id) {
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

    public void delete(final SocialNetworks socialNetworks) {
        final SocialNetworksJpaModel socialNetworksModel = mapper.toModel(socialNetworks);
        writeRepository.delete(socialNetworksModel);
    }

    public void delete(final Iterable<? extends SocialNetworks> entities) {
        final ArrayList<SocialNetworksJpaModel> models = new ArrayList<>();

        entities.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.delete(models);
    }

    public void deleteAll() {
        writeRepository.deleteAll();
    }



// MANY-TO-ONE
    public void deleteTalent(final String id) {
    final SocialNetworksJpaModel model = writeRepository.findOneWithTalent(id);
        model.setTalent(null);
        writeRepository.save(model);
    }

    public SocialNetworks findOneWithTalent(final String id) {
        return mapper.toDomain(writeRepository.findOneWithTalent(id));
    }

    @SuppressWarnings("unchecked")
    public Page<SocialNetworks> findByTalent(final String id,
                                                                        final FilterOptions filterOptions,
                                                                        final PageOptions pageOptions,
                                                                        final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Specification filteredSpecification = Specifications.where(SocialNetworksJpaSpecification.byTalentSpecification(id)).and(specification);

        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<SocialNetworksJpaModel> modelPage = (Page<SocialNetworksJpaModel>) writeRepository.findAll(filteredSpecification, pageable);
        final List<SocialNetworks> list = modelPage.getContent()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }
// END: MANY-TO-ONE



}
