package io.bandit.limbo.limbo.modules.main.talent.infrastructure;

import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaModel;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaRepository;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaSpecification;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaMapper;
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

@Named("TalentRepository")
@Transactional //Hibernate session depends on this.
public class TalentRepository {

    private final TalentJpaMapper mapper;
    private final TalentJpaRepository writeRepository;
    private final JpaConversion jpaConversion;

    @Inject
    public TalentRepository(final TalentJpaMapper mapper,
                                    final TalentJpaRepository writeRepository,
                                    final JpaConversion jpaConversion) {

        this.mapper = mapper;
        this.writeRepository = writeRepository;
        this.jpaConversion = jpaConversion;
    }

    public Talent findOne(final String id) {
        return mapper.toDomain(writeRepository.findOne(id));
    }

    public Page<Talent> findAll(final PageOptions pageOptions, final SortOptions sortOptions) {
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<TalentJpaModel> modelPage = writeRepository.findAll(pageable);
        final List<Talent> list = modelPage.getContent()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    public Talent save(final Talent talent) {
        final TalentJpaModel talentModel = writeRepository.save(mapper.toModel(talent));

        return mapper.toDomain(talentModel);
    }

    public List<Talent> findAll() {
        final List<TalentJpaModel> list = writeRepository.findAll();

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<Talent> findAll(final SortOptions sortOptions) {
        final List<TalentJpaModel> list = writeRepository.findAll(jpaConversion.fromSorting(sortOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public List<Talent> findAll(final Iterable<String> iterable) {
        final List<TalentJpaModel> list = writeRepository.findAll(iterable);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<Talent> findAll(final FilterOptions filterOptions) {
        final List<TalentJpaModel> list = writeRepository.findAll(jpaConversion.fromFilters(filterOptions));

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public Page<Talent> findAll(final FilterOptions filterOptions,
                                            final PageOptions pageOptions,
                                            final SortOptions sortOptions) {

        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Pageable pageable = jpaConversion.fromPage(pageOptions, sortOptions);
        final Page<TalentJpaModel> modelPage = writeRepository.findAll(specification, pageable);
        final List<Talent> list = modelPage.getContent()
            .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }

    @SuppressWarnings("unchecked")
    public List<Talent> findAll(final FilterOptions filterOptions, final SortOptions sortOptions) {
        final Specification specification = jpaConversion.fromFilters(filterOptions);
        final Sort sort = jpaConversion.fromSorting(sortOptions);
        final List<TalentJpaModel> list = writeRepository.findAll(specification, sort);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    public void flush() {
        writeRepository.flush();
    }

    public List<Talent> save(final Iterable<Talent> iterable) {
        final ArrayList<TalentJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        final List<TalentJpaModel> list = writeRepository.save(models);

        return list.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }


    public Talent saveAndFlush(final Talent talent) {
        final TalentJpaModel talentModel = writeRepository.saveAndFlush(mapper.toModel(talent));

        return mapper.toDomain(talentModel);
    }

    public void deleteInBatch(final Iterable<Talent> iterable) {
        final ArrayList<TalentJpaModel> models = new ArrayList<>();
        iterable.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.deleteInBatch(models);
    }

    public void deleteAllInBatch() {
        writeRepository.deleteAllInBatch();
    }

    public Talent getOne(final String id) {
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

    public void delete(final Talent talent) {
        final TalentJpaModel talentModel = mapper.toModel(talent);
        writeRepository.delete(talentModel);
    }

    public void delete(final Iterable<? extends Talent> entities) {
        final ArrayList<TalentJpaModel> models = new ArrayList<>();

        entities.iterator().forEachRemaining(v -> models.add(mapper.toModel(v)));
        writeRepository.delete(models);
    }

    public void deleteAll() {
        writeRepository.deleteAll();
    }




// ONE-TO-ONE
    public void deleteTalentProfile(final String id) {
        final TalentJpaModel model = writeRepository.findOneWithTalentProfile(id);
        model.setTalentProfile(null);
        writeRepository.save(model);
    }

    public Talent findOneWithTalentProfile(final String id) {
        return mapper.toDomain(writeRepository.findOneWithTalentProfile(id));
    }
// END: ONE-TO-ONE



// ONE-TO-ONE
    public void deleteTalentRole(final String id) {
        final TalentJpaModel model = writeRepository.findOneWithTalentRole(id);
        model.setTalentRole(null);
        writeRepository.save(model);
    }

    public Talent findOneWithTalentRole(final String id) {
        return mapper.toDomain(writeRepository.findOneWithTalentRole(id));
    }
// END: ONE-TO-ONE



// ONE-TO-ONE
    public void deleteCountry(final String id) {
        final TalentJpaModel model = writeRepository.findOneWithCountry(id);
        model.setCountry(null);
        writeRepository.save(model);
    }

    public Talent findOneWithCountry(final String id) {
        return mapper.toDomain(writeRepository.findOneWithCountry(id));
    }
// END: ONE-TO-ONE



// ONE-TO-ONE
    public void deleteCity(final String id) {
        final TalentJpaModel model = writeRepository.findOneWithCity(id);
        model.setCity(null);
        writeRepository.save(model);
    }

    public Talent findOneWithCity(final String id) {
        return mapper.toDomain(writeRepository.findOneWithCity(id));
    }
// END: ONE-TO-ONE



// ONE-TO-ONE
    public void deleteTalentTitle(final String id) {
        final TalentJpaModel model = writeRepository.findOneWithTalentTitle(id);
        model.setTalentTitle(null);
        writeRepository.save(model);
    }

    public Talent findOneWithTalentTitle(final String id) {
        return mapper.toDomain(writeRepository.findOneWithTalentTitle(id));
    }
// END: ONE-TO-ONE



// ONE-TO-ONE
    public void deleteTalentExperience(final String id) {
        final TalentJpaModel model = writeRepository.findOneWithTalentExperience(id);
        model.setTalentExperience(null);
        writeRepository.save(model);
    }

    public Talent findOneWithTalentExperience(final String id) {
        return mapper.toDomain(writeRepository.findOneWithTalentExperience(id));
    }
// END: ONE-TO-ONE



// ONE-TO-ONE
    public void deleteWorkType(final String id) {
        final TalentJpaModel model = writeRepository.findOneWithWorkType(id);
        model.setWorkType(null);
        writeRepository.save(model);
    }

    public Talent findOneWithWorkType(final String id) {
        return mapper.toDomain(writeRepository.findOneWithWorkType(id));
    }
// END: ONE-TO-ONE

// ONE-TO-MANY

    public Talent findOneWithSkills(final String id) {
        return mapper.toDomain(writeRepository.findOneWithSkills(id));
    }

    /**
     * Find the Talent that happen to have an instance of Skills with matching id.
     *
     * @param skillsId Skills identifier value.
     */
    @SuppressWarnings("unchecked")
    public Talent findTalentBySkills(final String skillsId) {

        final Specification<TalentJpaModel> specification = TalentJpaSpecification.bySkillsSpecification(skillsId);

        return mapper.toDomain((TalentJpaModel) writeRepository.findOne(specification));
    }

    /**
     * Remove the Talent that happen to have an instance of Skills with matching id.
     *
     * @param skillsId Skills identifier value.
     */
    @SuppressWarnings("unchecked")
    public void deleteTalentBySkills(final String skillsId) {
        final Specification<TalentJpaModel> specification = TalentJpaSpecification.bySkillsSpecification(skillsId);

        final TalentJpaModel model = (TalentJpaModel) writeRepository.findOne(specification);

        if (Optional.ofNullable(model).isPresent()) {
            writeRepository.delete(model);
        }
    }

    /**
     * Remove all Skills from instance Talent of matching id.
     *
     * @param talentId Talent identifier value.
     */
    @SuppressWarnings("unchecked")
    public void deleteAllSkills(final String talentId) {
        final TalentJpaModel talentModel = writeRepository.findOneWithSkills(talentId);

        talentModel.setSkills(null);
        writeRepository.save(talentModel);
    }

    /**
     * Remove all the listed Skills from instance Talent of matching id.
     *
     * @param talentId Talent identifier value.
     * @param skillsIds List of Skills ids.
     */
    @SuppressWarnings("unchecked")
    public void deleteAllSkills(final String talentId, final Iterable<String> skillsIds) {
        final TalentJpaModel talentModel = writeRepository.findOneWithSkills(talentId);

        final List<String> ids = new ArrayList<>();
        skillsIds.forEach(ids::add);

        talentModel
            .getSkills()
            .stream()
            .filter(skillsModel -> ids.contains(skillsModel.getId()))
            .forEach(skillsModel -> skillsModel.setTalent(null));

        writeRepository.save(talentModel);
    }

// END: ONE-TO-MANY



// ONE-TO-MANY

    public Talent findOneWithNotableProjects(final String id) {
        return mapper.toDomain(writeRepository.findOneWithNotableProjects(id));
    }

    /**
     * Find the Talent that happen to have an instance of NotableProjects with matching id.
     *
     * @param notableProjectsId NotableProjects identifier value.
     */
    @SuppressWarnings("unchecked")
    public Talent findTalentByNotableProjects(final String notableProjectsId) {

        final Specification<TalentJpaModel> specification = TalentJpaSpecification.byNotableProjectsSpecification(notableProjectsId);

        return mapper.toDomain((TalentJpaModel) writeRepository.findOne(specification));
    }

    /**
     * Remove the Talent that happen to have an instance of NotableProjects with matching id.
     *
     * @param notableProjectsId NotableProjects identifier value.
     */
    @SuppressWarnings("unchecked")
    public void deleteTalentByNotableProjects(final String notableProjectsId) {
        final Specification<TalentJpaModel> specification = TalentJpaSpecification.byNotableProjectsSpecification(notableProjectsId);

        final TalentJpaModel model = (TalentJpaModel) writeRepository.findOne(specification);

        if (Optional.ofNullable(model).isPresent()) {
            writeRepository.delete(model);
        }
    }

    /**
     * Remove all NotableProjects from instance Talent of matching id.
     *
     * @param talentId Talent identifier value.
     */
    @SuppressWarnings("unchecked")
    public void deleteAllNotableProjects(final String talentId) {
        final TalentJpaModel talentModel = writeRepository.findOneWithNotableProjects(talentId);

        talentModel.setNotableProjects(null);
        writeRepository.save(talentModel);
    }

    /**
     * Remove all the listed NotableProjects from instance Talent of matching id.
     *
     * @param talentId Talent identifier value.
     * @param notableProjectsIds List of NotableProjects ids.
     */
    @SuppressWarnings("unchecked")
    public void deleteAllNotableProjects(final String talentId, final Iterable<String> notableProjectsIds) {
        final TalentJpaModel talentModel = writeRepository.findOneWithNotableProjects(talentId);

        final List<String> ids = new ArrayList<>();
        notableProjectsIds.forEach(ids::add);

        talentModel
            .getNotableProjects()
            .stream()
            .filter(notableProjectsModel -> ids.contains(notableProjectsModel.getId()))
            .forEach(notableProjectsModel -> notableProjectsModel.setTalent(null));

        writeRepository.save(talentModel);
    }

// END: ONE-TO-MANY



// ONE-TO-MANY

    public Talent findOneWithCompanyTraits(final String id) {
        return mapper.toDomain(writeRepository.findOneWithCompanyTraits(id));
    }

    /**
     * Find the Talent that happen to have an instance of CompanyTraits with matching id.
     *
     * @param companyTraitsId CompanyTraits identifier value.
     */
    @SuppressWarnings("unchecked")
    public Talent findTalentByCompanyTraits(final String companyTraitsId) {

        final Specification<TalentJpaModel> specification = TalentJpaSpecification.byCompanyTraitsSpecification(companyTraitsId);

        return mapper.toDomain((TalentJpaModel) writeRepository.findOne(specification));
    }

    /**
     * Remove the Talent that happen to have an instance of CompanyTraits with matching id.
     *
     * @param companyTraitsId CompanyTraits identifier value.
     */
    @SuppressWarnings("unchecked")
    public void deleteTalentByCompanyTraits(final String companyTraitsId) {
        final Specification<TalentJpaModel> specification = TalentJpaSpecification.byCompanyTraitsSpecification(companyTraitsId);

        final TalentJpaModel model = (TalentJpaModel) writeRepository.findOne(specification);

        if (Optional.ofNullable(model).isPresent()) {
            writeRepository.delete(model);
        }
    }

    /**
     * Remove all CompanyTraits from instance Talent of matching id.
     *
     * @param talentId Talent identifier value.
     */
    @SuppressWarnings("unchecked")
    public void deleteAllCompanyTraits(final String talentId) {
        final TalentJpaModel talentModel = writeRepository.findOneWithCompanyTraits(talentId);

        talentModel.setCompanyTraits(null);
        writeRepository.save(talentModel);
    }

    /**
     * Remove all the listed CompanyTraits from instance Talent of matching id.
     *
     * @param talentId Talent identifier value.
     * @param companyTraitsIds List of CompanyTraits ids.
     */
    @SuppressWarnings("unchecked")
    public void deleteAllCompanyTraits(final String talentId, final Iterable<String> companyTraitsIds) {
        final TalentJpaModel talentModel = writeRepository.findOneWithCompanyTraits(talentId);

        final List<String> ids = new ArrayList<>();
        companyTraitsIds.forEach(ids::add);

        talentModel
            .getCompanyTraits()
            .stream()
            .filter(companyTraitsModel -> ids.contains(companyTraitsModel.getId()))
            .forEach(companyTraitsModel -> companyTraitsModel.setTalent(null));

        writeRepository.save(talentModel);
    }

// END: ONE-TO-MANY



// ONE-TO-MANY

    public Talent findOneWithPersonalTraits(final String id) {
        return mapper.toDomain(writeRepository.findOneWithPersonalTraits(id));
    }

    /**
     * Find the Talent that happen to have an instance of PersonalTraits with matching id.
     *
     * @param personalTraitsId PersonalTraits identifier value.
     */
    @SuppressWarnings("unchecked")
    public Talent findTalentByPersonalTraits(final String personalTraitsId) {

        final Specification<TalentJpaModel> specification = TalentJpaSpecification.byPersonalTraitsSpecification(personalTraitsId);

        return mapper.toDomain((TalentJpaModel) writeRepository.findOne(specification));
    }

    /**
     * Remove the Talent that happen to have an instance of PersonalTraits with matching id.
     *
     * @param personalTraitsId PersonalTraits identifier value.
     */
    @SuppressWarnings("unchecked")
    public void deleteTalentByPersonalTraits(final String personalTraitsId) {
        final Specification<TalentJpaModel> specification = TalentJpaSpecification.byPersonalTraitsSpecification(personalTraitsId);

        final TalentJpaModel model = (TalentJpaModel) writeRepository.findOne(specification);

        if (Optional.ofNullable(model).isPresent()) {
            writeRepository.delete(model);
        }
    }

    /**
     * Remove all PersonalTraits from instance Talent of matching id.
     *
     * @param talentId Talent identifier value.
     */
    @SuppressWarnings("unchecked")
    public void deleteAllPersonalTraits(final String talentId) {
        final TalentJpaModel talentModel = writeRepository.findOneWithPersonalTraits(talentId);

        talentModel.setPersonalTraits(null);
        writeRepository.save(talentModel);
    }

    /**
     * Remove all the listed PersonalTraits from instance Talent of matching id.
     *
     * @param talentId Talent identifier value.
     * @param personalTraitsIds List of PersonalTraits ids.
     */
    @SuppressWarnings("unchecked")
    public void deleteAllPersonalTraits(final String talentId, final Iterable<String> personalTraitsIds) {
        final TalentJpaModel talentModel = writeRepository.findOneWithPersonalTraits(talentId);

        final List<String> ids = new ArrayList<>();
        personalTraitsIds.forEach(ids::add);

        talentModel
            .getPersonalTraits()
            .stream()
            .filter(personalTraitsModel -> ids.contains(personalTraitsModel.getId()))
            .forEach(personalTraitsModel -> personalTraitsModel.setTalent(null));

        writeRepository.save(talentModel);
    }

// END: ONE-TO-MANY



// ONE-TO-MANY

    public Talent findOneWithSocialNetworks(final String id) {
        return mapper.toDomain(writeRepository.findOneWithSocialNetworks(id));
    }

    /**
     * Find the Talent that happen to have an instance of SocialNetworks with matching id.
     *
     * @param socialNetworksId SocialNetworks identifier value.
     */
    @SuppressWarnings("unchecked")
    public Talent findTalentBySocialNetworks(final String socialNetworksId) {

        final Specification<TalentJpaModel> specification = TalentJpaSpecification.bySocialNetworksSpecification(socialNetworksId);

        return mapper.toDomain((TalentJpaModel) writeRepository.findOne(specification));
    }

    /**
     * Remove the Talent that happen to have an instance of SocialNetworks with matching id.
     *
     * @param socialNetworksId SocialNetworks identifier value.
     */
    @SuppressWarnings("unchecked")
    public void deleteTalentBySocialNetworks(final String socialNetworksId) {
        final Specification<TalentJpaModel> specification = TalentJpaSpecification.bySocialNetworksSpecification(socialNetworksId);

        final TalentJpaModel model = (TalentJpaModel) writeRepository.findOne(specification);

        if (Optional.ofNullable(model).isPresent()) {
            writeRepository.delete(model);
        }
    }

    /**
     * Remove all SocialNetworks from instance Talent of matching id.
     *
     * @param talentId Talent identifier value.
     */
    @SuppressWarnings("unchecked")
    public void deleteAllSocialNetworks(final String talentId) {
        final TalentJpaModel talentModel = writeRepository.findOneWithSocialNetworks(talentId);

        talentModel.setSocialNetworks(null);
        writeRepository.save(talentModel);
    }

    /**
     * Remove all the listed SocialNetworks from instance Talent of matching id.
     *
     * @param talentId Talent identifier value.
     * @param socialNetworksIds List of SocialNetworks ids.
     */
    @SuppressWarnings("unchecked")
    public void deleteAllSocialNetworks(final String talentId, final Iterable<String> socialNetworksIds) {
        final TalentJpaModel talentModel = writeRepository.findOneWithSocialNetworks(talentId);

        final List<String> ids = new ArrayList<>();
        socialNetworksIds.forEach(ids::add);

        talentModel
            .getSocialNetworks()
            .stream()
            .filter(socialNetworksModel -> ids.contains(socialNetworksModel.getId()))
            .forEach(socialNetworksModel -> socialNetworksModel.setTalent(null));

        writeRepository.save(talentModel);
    }

// END: ONE-TO-MANY



// ONE-TO-MANY

    public Talent findOneWithJobOffers(final String id) {
        return mapper.toDomain(writeRepository.findOneWithJobOffers(id));
    }

    /**
     * Find the Talent that happen to have an instance of JobOffer with matching id.
     *
     * @param jobOfferId JobOffer identifier value.
     */
    @SuppressWarnings("unchecked")
    public Talent findTalentByJobOffer(final String jobOfferId) {

        final Specification<TalentJpaModel> specification = TalentJpaSpecification.byJobOfferSpecification(jobOfferId);

        return mapper.toDomain((TalentJpaModel) writeRepository.findOne(specification));
    }

    /**
     * Remove the Talent that happen to have an instance of JobOffer with matching id.
     *
     * @param jobOfferId JobOffer identifier value.
     */
    @SuppressWarnings("unchecked")
    public void deleteTalentByJobOffer(final String jobOfferId) {
        final Specification<TalentJpaModel> specification = TalentJpaSpecification.byJobOfferSpecification(jobOfferId);

        final TalentJpaModel model = (TalentJpaModel) writeRepository.findOne(specification);

        if (Optional.ofNullable(model).isPresent()) {
            writeRepository.delete(model);
        }
    }

    /**
     * Remove all JobOffers from instance Talent of matching id.
     *
     * @param talentId Talent identifier value.
     */
    @SuppressWarnings("unchecked")
    public void deleteAllJobOffers(final String talentId) {
        final TalentJpaModel talentModel = writeRepository.findOneWithJobOffers(talentId);

        talentModel.setJobOffers(null);
        writeRepository.save(talentModel);
    }

    /**
     * Remove all the listed JobOffers from instance Talent of matching id.
     *
     * @param talentId Talent identifier value.
     * @param jobOfferIds List of JobOffer ids.
     */
    @SuppressWarnings("unchecked")
    public void deleteAllJobOffers(final String talentId, final Iterable<String> jobOfferIds) {
        final TalentJpaModel talentModel = writeRepository.findOneWithJobOffers(talentId);

        final List<String> ids = new ArrayList<>();
        jobOfferIds.forEach(ids::add);

        talentModel
            .getJobOffers()
            .stream()
            .filter(jobOfferModel -> ids.contains(jobOfferModel.getId()))
            .forEach(jobOfferModel -> jobOfferModel.setTalent(null));

        writeRepository.save(talentModel);
    }

// END: ONE-TO-MANY




}
