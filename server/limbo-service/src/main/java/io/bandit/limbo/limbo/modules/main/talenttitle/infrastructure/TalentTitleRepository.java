package io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure;

import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.jpa.TalentTitleJpaModel;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.jpa.TalentTitleJpaRepository;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.jpa.TalentTitleJpaSpecification;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.jpa.TalentTitleJpaMapper;
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

@Named("TalentTitleRepository")
@Transactional //Hibernate session depends on this.
public class TalentTitleRepository {

    private final TalentTitleJpaMapper mapper;
    private final TalentTitleJpaRepository writeRepository;
    private final JpaConversion jpaConversion;

    @Inject
    public TalentTitleRepository(final TalentTitleJpaMapper mapper,
                                    final TalentTitleJpaRepository writeRepository,
                                    final JpaConversion jpaConversion) {

        this.mapper = mapper;
        this.writeRepository = writeRepository;
        this.jpaConversion = jpaConversion;
    }

    public TalentTitle findOne(final String id) {
        return mapper.toDomain(writeRepository.findOne(id));
    }

    public Page<TalentTitle> findAll(final PageOptions pageOptions, final SortOptions sortOptions) {
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<TalentTitleJpaModel> modelPage = writeRepository.findAll(pageable);
        final List<TalentTitle> list = modelPage.getContent()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    public TalentTitle save(final TalentTitle talentTitle) {
        final TalentTitleJpaModel talentTitleModel = writeRepository.save(mapper.toModel(talentTitle));

        return mapper.toDomain(talentTitleModel);
    }

    public List<TalentTitle> findAll() {
        final List<TalentTitleJpaModel> list = writeRepository.findAll();

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<TalentTitle> findAll(final SortOptions sortOptions) {
        final List<TalentTitleJpaModel> list = writeRepository.findAll(jpaConversion.fromSorting(sortOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<TalentTitle> findAll(final Iterable<String> iterable) {
        final List<TalentTitleJpaModel> list = writeRepository.findAll(iterable);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<TalentTitle> findAll(final FilterOptions filterOptions) {
        final List<TalentTitleJpaModel> list = writeRepository.findAll(jpaConversion.fromFilters(filterOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public Page<TalentTitle> findAll(final FilterOptions filterOptions,
                                            final PageOptions pageOptions,
                                            final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<TalentTitleJpaModel> modelPage = writeRepository.findAll(specification, pageable);
        final List<TalentTitle> list = modelPage.getContent()
            .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    @SuppressWarnings("unchecked")
    public List<TalentTitle> findAll(final FilterOptions filterOptions, final SortOptions sortOptions) {
        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Sort sort = jpaConversion.fromSorting(sortOptions);
        final List<TalentTitleJpaModel> list = writeRepository.findAll(specification, sort);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public void flush() {
        writeRepository.flush();
    }

    public List<TalentTitle> save(final Iterable<TalentTitle> iterable) {
        final ArrayList<TalentTitleJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        final List<TalentTitleJpaModel> list = writeRepository.save(models);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }


    public TalentTitle saveAndFlush(final TalentTitle talentTitle) {
        final TalentTitleJpaModel talentTitleModel = writeRepository.saveAndFlush(mapper.toModel(talentTitle));

        return mapper.toDomain(talentTitleModel);
    }

    public void deleteInBatch(final Iterable<TalentTitle> iterable) {
        final ArrayList<TalentTitleJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.deleteInBatch(models);
    }

    public void deleteAllInBatch() {
        writeRepository.deleteAllInBatch();
    }

    public TalentTitle getOne(final String id) {
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

    public void delete(final TalentTitle talentTitle) {
        final TalentTitleJpaModel talentTitleModel = mapper.toModel(talentTitle);
        writeRepository.delete(talentTitleModel);
    }

    public void delete(final Iterable<? extends TalentTitle> entities) {
        final ArrayList<TalentTitleJpaModel> models = new ArrayList<>();

        entities.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.delete(models);
    }

    public void deleteAll() {
        writeRepository.deleteAll();
    }



}
