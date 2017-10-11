package io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaModel;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaRepository;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.jpa.TalentProfileJpaMapper;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.jpa.TalentProfileJpaModel;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.jpa.TalentProfileJpaRepository;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.jpa.TalentProfileJpaMapper;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.jpa.TalentRoleJpaMapper;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.jpa.TalentRoleJpaModel;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.jpa.TalentRoleJpaRepository;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.jpa.TalentRoleJpaMapper;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaMapper;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaModel;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaRepository;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaMapper;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaMapper;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaModel;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaRepository;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaMapper;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.jpa.TalentTitleJpaMapper;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.jpa.TalentTitleJpaModel;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.jpa.TalentTitleJpaRepository;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.jpa.TalentTitleJpaMapper;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.jpa.TalentExperienceJpaMapper;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.jpa.TalentExperienceJpaModel;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.jpa.TalentExperienceJpaRepository;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.jpa.TalentExperienceJpaMapper;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.jpa.WorkTypeJpaMapper;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.jpa.WorkTypeJpaModel;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.jpa.WorkTypeJpaRepository;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.jpa.WorkTypeJpaMapper;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.jpa.SkillsJpaMapper;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.jpa.SkillsJpaModel;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.jpa.SkillsJpaRepository;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.jpa.NotableProjectsJpaMapper;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.jpa.NotableProjectsJpaModel;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.jpa.NotableProjectsJpaRepository;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.jpa.CompanyTraitsJpaMapper;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.jpa.CompanyTraitsJpaModel;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.jpa.CompanyTraitsJpaRepository;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.jpa.PersonalTraitsJpaMapper;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.jpa.PersonalTraitsJpaModel;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.jpa.PersonalTraitsJpaRepository;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.jpa.SocialNetworksJpaMapper;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.jpa.SocialNetworksJpaModel;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.jpa.SocialNetworksJpaRepository;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.jpa.JobOfferJpaMapper;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.jpa.JobOfferJpaModel;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.jpa.JobOfferJpaRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@Named("TalentJpaMapper")
public class TalentJpaMapper {

    @Inject private TalentJpaRepository talentJpaRepository;
    @Inject private TalentProfileJpaRepository talentProfileJpaRepository;
    @Inject private TalentProfileJpaMapper talentProfileJpaMapper;
    @Inject private TalentRoleJpaRepository talentRoleJpaRepository;
    @Inject private TalentRoleJpaMapper talentRoleJpaMapper;
    @Inject private CountryJpaRepository countryJpaRepository;
    @Inject private CountryJpaMapper countryJpaMapper;
    @Inject private CityJpaRepository cityJpaRepository;
    @Inject private CityJpaMapper cityJpaMapper;
    @Inject private TalentTitleJpaRepository talentTitleJpaRepository;
    @Inject private TalentTitleJpaMapper talentTitleJpaMapper;
    @Inject private TalentExperienceJpaRepository talentExperienceJpaRepository;
    @Inject private TalentExperienceJpaMapper talentExperienceJpaMapper;
    @Inject private WorkTypeJpaRepository workTypeJpaRepository;
    @Inject private WorkTypeJpaMapper workTypeJpaMapper;
    @Inject private SkillsJpaRepository skillsJpaRepository;
    @Inject private SkillsJpaMapper skillsJpaMapper;
    @Inject private NotableProjectsJpaRepository notableProjectsJpaRepository;
    @Inject private NotableProjectsJpaMapper notableProjectsJpaMapper;
    @Inject private CompanyTraitsJpaRepository companyTraitsJpaRepository;
    @Inject private CompanyTraitsJpaMapper companyTraitsJpaMapper;
    @Inject private PersonalTraitsJpaRepository personalTraitsJpaRepository;
    @Inject private PersonalTraitsJpaMapper personalTraitsJpaMapper;
    @Inject private SocialNetworksJpaRepository socialNetworksJpaRepository;
    @Inject private SocialNetworksJpaMapper socialNetworksJpaMapper;
    @Inject private JobOfferJpaRepository jobOfferJpaRepository;
    @Inject private JobOfferJpaMapper jobOfferJpaMapper;

    public Talent toDomain(final TalentJpaModel model) {

        if (Optional.ofNullable(model).isPresent()) {
            try {
                final Talent talent = new Talent();

                talent.setId(model.getId());
                talent.setEmail(model.getEmail());
                talent.setPassword(model.getPassword());
                talent.setTalentProfile(talentProfileJpaMapper.toDomain(model.getTalentProfile()));
                talent.setTalentRole(talentRoleJpaMapper.toDomain(model.getTalentRole()));
                talent.setCountry(countryJpaMapper.toDomain(model.getCountry()));
                talent.setCity(cityJpaMapper.toDomain(model.getCity()));
                talent.setTalentTitle(talentTitleJpaMapper.toDomain(model.getTalentTitle()));
                talent.setTalentExperience(talentExperienceJpaMapper.toDomain(model.getTalentExperience()));
                talent.setWorkType(workTypeJpaMapper.toDomain(model.getWorkType()));
                setSkillsDomain(talent, model);
                setNotableProjectsDomain(talent, model);
                setCompanyTraitsDomain(talent, model);
                setPersonalTraitsDomain(talent, model);
                setSocialNetworksDomain(talent, model);
                setJobOfferDomain(talent, model);

                return talent;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Converts Skills model data to domain data.
     *
     * @param talent  The domain class representation for Talent.
     * @param model  The model class representation for Talent.
     */
    private void setSkillsDomain(final Talent talent, TalentJpaModel model) {
        talent.setSkills(model.getSkills()
             .stream()
             .map(skillsJpaMapper::toDomain)
             .collect(Collectors.toSet())
        );
    }

    /**
     * Converts NotableProjects model data to domain data.
     *
     * @param talent  The domain class representation for Talent.
     * @param model  The model class representation for Talent.
     */
    private void setNotableProjectsDomain(final Talent talent, TalentJpaModel model) {
        talent.setNotableProjects(model.getNotableProjects()
             .stream()
             .map(notableProjectsJpaMapper::toDomain)
             .collect(Collectors.toSet())
        );
    }

    /**
     * Converts CompanyTraits model data to domain data.
     *
     * @param talent  The domain class representation for Talent.
     * @param model  The model class representation for Talent.
     */
    private void setCompanyTraitsDomain(final Talent talent, TalentJpaModel model) {
        talent.setCompanyTraits(model.getCompanyTraits()
             .stream()
             .map(companyTraitsJpaMapper::toDomain)
             .collect(Collectors.toSet())
        );
    }

    /**
     * Converts PersonalTraits model data to domain data.
     *
     * @param talent  The domain class representation for Talent.
     * @param model  The model class representation for Talent.
     */
    private void setPersonalTraitsDomain(final Talent talent, TalentJpaModel model) {
        talent.setPersonalTraits(model.getPersonalTraits()
             .stream()
             .map(personalTraitsJpaMapper::toDomain)
             .collect(Collectors.toSet())
        );
    }

    /**
     * Converts SocialNetworks model data to domain data.
     *
     * @param talent  The domain class representation for Talent.
     * @param model  The model class representation for Talent.
     */
    private void setSocialNetworksDomain(final Talent talent, TalentJpaModel model) {
        talent.setSocialNetworks(model.getSocialNetworks()
             .stream()
             .map(socialNetworksJpaMapper::toDomain)
             .collect(Collectors.toSet())
        );
    }

    /**
     * Converts JobOffer model data to domain data.
     *
     * @param talent  The domain class representation for Talent.
     * @param model  The model class representation for Talent.
     */
    private void setJobOfferDomain(final Talent talent, TalentJpaModel model) {
        talent.setJobOffers(model.getJobOffers()
             .stream()
             .map(jobOfferJpaMapper::toDomain)
             .collect(Collectors.toSet())
        );
    }

    public TalentJpaModel toModel(final Talent talent) {

        if (Optional.ofNullable(talent).isPresent()) {
            try {
                TalentJpaModel model = new TalentJpaModel();
                if (Optional.ofNullable(talent.getId()).isPresent()) {
                    final TalentJpaModel dbModel = talentJpaRepository.findOne(talent.getId());
                    if (null != dbModel) {
                        model = dbModel;
                    }
                }

                model.setId(talent.getId());
                model.setEmail(talent.getEmail());
                model.setPassword(talent.getPassword());

                oneToOneTalentProfileRelationship(talent, model);
                oneToOneTalentRoleRelationship(talent, model);
                oneToOneCountryRelationship(talent, model);
                oneToOneCityRelationship(talent, model);
                oneToOneTalentTitleRelationship(talent, model);
                oneToOneTalentExperienceRelationship(talent, model);
                oneToOneWorkTypeRelationship(talent, model);
                oneToManySkillsRelationship(talent, model);
                oneToManyNotableProjectsRelationship(talent, model);
                oneToManyCompanyTraitsRelationship(talent, model);
                oneToManyPersonalTraitsRelationship(talent, model);
                oneToManySocialNetworksRelationship(talent, model);
                oneToManyJobOfferRelationship(talent, model);

                return model;

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }
   /**
    * Sets up the one to one relationship between Talent and TalentProfile.
    *
    * @param talent  The domain class representation for Talent.
    * @param model  The model class representation for Talent.
    */
    private void oneToOneTalentProfileRelationship(final Talent talent, final TalentJpaModel model) {

        if (Optional.ofNullable(talent.getTalentProfile()).isPresent()) {
            final TalentProfile talentProfile = talent.getTalentProfile();
            final String id = talentProfile.getId();

            TalentProfileJpaModel talentProfileModel = null;
            if (Optional.ofNullable(talentProfile.getId()).isPresent()) {
                talentProfileModel = talentProfileJpaRepository.findOne(id);
            }

            if (Objects.equals(model.getTalentProfile(), talentProfileModel) && Optional.ofNullable(model.getTalentProfile()).isPresent()) {
                return;
            }

            if (!Optional.ofNullable(talentProfileModel).isPresent()) {
                talentProfileModel = talentProfileJpaMapper.toModel(talentProfile);
            }

            if (null != model.getTalentProfile()) {
                talentProfileJpaRepository.delete(model.getTalentProfile());
            }

            model.setTalentProfile(talentProfileModel);
        }
    }

   /**
    * Sets up the one to one relationship between Talent and TalentRole.
    *
    * @param talent  The domain class representation for Talent.
    * @param model  The model class representation for Talent.
    */
    private void oneToOneTalentRoleRelationship(final Talent talent, final TalentJpaModel model) {

        if (Optional.ofNullable(talent.getTalentRole()).isPresent()) {
            final TalentRole talentRole = talent.getTalentRole();
            final String id = talentRole.getId();

            TalentRoleJpaModel talentRoleModel = null;
            if (Optional.ofNullable(talentRole.getId()).isPresent()) {
                talentRoleModel = talentRoleJpaRepository.findOne(id);
            }

            if (Objects.equals(model.getTalentRole(), talentRoleModel) && Optional.ofNullable(model.getTalentRole()).isPresent()) {
                return;
            }

            if (!Optional.ofNullable(talentRoleModel).isPresent()) {
                talentRoleModel = talentRoleJpaMapper.toModel(talentRole);
            }

            if (null != model.getTalentRole()) {
                talentRoleJpaRepository.delete(model.getTalentRole());
            }

            model.setTalentRole(talentRoleModel);
        }
    }

   /**
    * Sets up the one to one relationship between Talent and Country.
    *
    * @param talent  The domain class representation for Talent.
    * @param model  The model class representation for Talent.
    */
    private void oneToOneCountryRelationship(final Talent talent, final TalentJpaModel model) {

        if (Optional.ofNullable(talent.getCountry()).isPresent()) {
            final Country country = talent.getCountry();
            final String id = country.getId();

            CountryJpaModel countryModel = null;
            if (Optional.ofNullable(country.getId()).isPresent()) {
                countryModel = countryJpaRepository.findOne(id);
            }

            if (Objects.equals(model.getCountry(), countryModel) && Optional.ofNullable(model.getCountry()).isPresent()) {
                return;
            }

            if (!Optional.ofNullable(countryModel).isPresent()) {
                countryModel = countryJpaMapper.toModel(country);
            }

            if (null != model.getCountry()) {
                countryJpaRepository.delete(model.getCountry());
            }

            model.setCountry(countryModel);
        }
    }

   /**
    * Sets up the one to one relationship between Talent and City.
    *
    * @param talent  The domain class representation for Talent.
    * @param model  The model class representation for Talent.
    */
    private void oneToOneCityRelationship(final Talent talent, final TalentJpaModel model) {

        if (Optional.ofNullable(talent.getCity()).isPresent()) {
            final City city = talent.getCity();
            final String id = city.getId();

            CityJpaModel cityModel = null;
            if (Optional.ofNullable(city.getId()).isPresent()) {
                cityModel = cityJpaRepository.findOne(id);
            }

            if (Objects.equals(model.getCity(), cityModel) && Optional.ofNullable(model.getCity()).isPresent()) {
                return;
            }

            if (!Optional.ofNullable(cityModel).isPresent()) {
                cityModel = cityJpaMapper.toModel(city);
            }

            if (null != model.getCity()) {
                cityJpaRepository.delete(model.getCity());
            }

            model.setCity(cityModel);
        }
    }

   /**
    * Sets up the one to one relationship between Talent and TalentTitle.
    *
    * @param talent  The domain class representation for Talent.
    * @param model  The model class representation for Talent.
    */
    private void oneToOneTalentTitleRelationship(final Talent talent, final TalentJpaModel model) {

        if (Optional.ofNullable(talent.getTalentTitle()).isPresent()) {
            final TalentTitle talentTitle = talent.getTalentTitle();
            final String id = talentTitle.getId();

            TalentTitleJpaModel talentTitleModel = null;
            if (Optional.ofNullable(talentTitle.getId()).isPresent()) {
                talentTitleModel = talentTitleJpaRepository.findOne(id);
            }

            if (Objects.equals(model.getTalentTitle(), talentTitleModel) && Optional.ofNullable(model.getTalentTitle()).isPresent()) {
                return;
            }

            if (!Optional.ofNullable(talentTitleModel).isPresent()) {
                talentTitleModel = talentTitleJpaMapper.toModel(talentTitle);
            }

            if (null != model.getTalentTitle()) {
                talentTitleJpaRepository.delete(model.getTalentTitle());
            }

            model.setTalentTitle(talentTitleModel);
        }
    }

   /**
    * Sets up the one to one relationship between Talent and TalentExperience.
    *
    * @param talent  The domain class representation for Talent.
    * @param model  The model class representation for Talent.
    */
    private void oneToOneTalentExperienceRelationship(final Talent talent, final TalentJpaModel model) {

        if (Optional.ofNullable(talent.getTalentExperience()).isPresent()) {
            final TalentExperience talentExperience = talent.getTalentExperience();
            final String id = talentExperience.getId();

            TalentExperienceJpaModel talentExperienceModel = null;
            if (Optional.ofNullable(talentExperience.getId()).isPresent()) {
                talentExperienceModel = talentExperienceJpaRepository.findOne(id);
            }

            if (Objects.equals(model.getTalentExperience(), talentExperienceModel) && Optional.ofNullable(model.getTalentExperience()).isPresent()) {
                return;
            }

            if (!Optional.ofNullable(talentExperienceModel).isPresent()) {
                talentExperienceModel = talentExperienceJpaMapper.toModel(talentExperience);
            }

            if (null != model.getTalentExperience()) {
                talentExperienceJpaRepository.delete(model.getTalentExperience());
            }

            model.setTalentExperience(talentExperienceModel);
        }
    }

   /**
    * Sets up the one to one relationship between Talent and WorkType.
    *
    * @param talent  The domain class representation for Talent.
    * @param model  The model class representation for Talent.
    */
    private void oneToOneWorkTypeRelationship(final Talent talent, final TalentJpaModel model) {

        if (Optional.ofNullable(talent.getWorkType()).isPresent()) {
            final WorkType workType = talent.getWorkType();
            final String id = workType.getId();

            WorkTypeJpaModel workTypeModel = null;
            if (Optional.ofNullable(workType.getId()).isPresent()) {
                workTypeModel = workTypeJpaRepository.findOne(id);
            }

            if (Objects.equals(model.getWorkType(), workTypeModel) && Optional.ofNullable(model.getWorkType()).isPresent()) {
                return;
            }

            if (!Optional.ofNullable(workTypeModel).isPresent()) {
                workTypeModel = workTypeJpaMapper.toModel(workType);
            }

            if (null != model.getWorkType()) {
                workTypeJpaRepository.delete(model.getWorkType());
            }

            model.setWorkType(workTypeModel);
        }
    }

   /**
    * Sets up the many to many relationship between Talent and Skills.
    *
    * @param talent  The domain class representation for Talent.
    * @param model  The model class representation for Talent.
    */
    private void oneToManySkillsRelationship(final Talent talent, final TalentJpaModel model) {

        final Optional<Set<Skills>> optional = Optional.ofNullable(talent.getSkills());
        if (!optional.isPresent()) {
            return;
        }

        final Stream<Skills> skills = talent.getSkills().stream();
        skills.forEach(v -> model.addSkills(skillsJpaMapper.toModel(v)));

        if (Optional.ofNullable(model.getSkills()).isPresent()) {
            skillsJpaRepository.save(model.getSkills());
        }
    }

   /**
    * Sets up the many to many relationship between Talent and NotableProjects.
    *
    * @param talent  The domain class representation for Talent.
    * @param model  The model class representation for Talent.
    */
    private void oneToManyNotableProjectsRelationship(final Talent talent, final TalentJpaModel model) {

        final Optional<Set<NotableProjects>> optional = Optional.ofNullable(talent.getNotableProjects());
        if (!optional.isPresent()) {
            return;
        }

        final Stream<NotableProjects> notableProjects = talent.getNotableProjects().stream();
        notableProjects.forEach(v -> model.addNotableProjects(notableProjectsJpaMapper.toModel(v)));

        if (Optional.ofNullable(model.getNotableProjects()).isPresent()) {
            notableProjectsJpaRepository.save(model.getNotableProjects());
        }
    }

   /**
    * Sets up the many to many relationship between Talent and CompanyTraits.
    *
    * @param talent  The domain class representation for Talent.
    * @param model  The model class representation for Talent.
    */
    private void oneToManyCompanyTraitsRelationship(final Talent talent, final TalentJpaModel model) {

        final Optional<Set<CompanyTraits>> optional = Optional.ofNullable(talent.getCompanyTraits());
        if (!optional.isPresent()) {
            return;
        }

        final Stream<CompanyTraits> companyTraits = talent.getCompanyTraits().stream();
        companyTraits.forEach(v -> model.addCompanyTraits(companyTraitsJpaMapper.toModel(v)));

        if (Optional.ofNullable(model.getCompanyTraits()).isPresent()) {
            companyTraitsJpaRepository.save(model.getCompanyTraits());
        }
    }

   /**
    * Sets up the many to many relationship between Talent and PersonalTraits.
    *
    * @param talent  The domain class representation for Talent.
    * @param model  The model class representation for Talent.
    */
    private void oneToManyPersonalTraitsRelationship(final Talent talent, final TalentJpaModel model) {

        final Optional<Set<PersonalTraits>> optional = Optional.ofNullable(talent.getPersonalTraits());
        if (!optional.isPresent()) {
            return;
        }

        final Stream<PersonalTraits> personalTraits = talent.getPersonalTraits().stream();
        personalTraits.forEach(v -> model.addPersonalTraits(personalTraitsJpaMapper.toModel(v)));

        if (Optional.ofNullable(model.getPersonalTraits()).isPresent()) {
            personalTraitsJpaRepository.save(model.getPersonalTraits());
        }
    }

   /**
    * Sets up the many to many relationship between Talent and SocialNetworks.
    *
    * @param talent  The domain class representation for Talent.
    * @param model  The model class representation for Talent.
    */
    private void oneToManySocialNetworksRelationship(final Talent talent, final TalentJpaModel model) {

        final Optional<Set<SocialNetworks>> optional = Optional.ofNullable(talent.getSocialNetworks());
        if (!optional.isPresent()) {
            return;
        }

        final Stream<SocialNetworks> socialNetworks = talent.getSocialNetworks().stream();
        socialNetworks.forEach(v -> model.addSocialNetworks(socialNetworksJpaMapper.toModel(v)));

        if (Optional.ofNullable(model.getSocialNetworks()).isPresent()) {
            socialNetworksJpaRepository.save(model.getSocialNetworks());
        }
    }

   /**
    * Sets up the many to many relationship between Talent and JobOffer.
    *
    * @param talent  The domain class representation for Talent.
    * @param model  The model class representation for Talent.
    */
    private void oneToManyJobOfferRelationship(final Talent talent, final TalentJpaModel model) {

        final Optional<Set<JobOffer>> optional = Optional.ofNullable(talent.getJobOffers());
        if (!optional.isPresent()) {
            return;
        }

        final Stream<JobOffer> jobOffers = talent.getJobOffers().stream();
        jobOffers.forEach(v -> model.addJobOffer(jobOfferJpaMapper.toModel(v)));

        if (Optional.ofNullable(model.getJobOffers()).isPresent()) {
            jobOfferJpaRepository.save(model.getJobOffers());
        }
    }

}
//isManyToMany: 
//isManyToOne: 
//isOneToOne: true
//isAlone: 