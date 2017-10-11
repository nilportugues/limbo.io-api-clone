package io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.jpa.TalentProfileJpaModel;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.jpa.TalentRoleJpaModel;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaModel;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaModel;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.jpa.TalentTitleJpaModel;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.jpa.TalentExperienceJpaModel;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.jpa.WorkTypeJpaModel;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.jpa.SkillsJpaModel;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.jpa.NotableProjectsJpaModel;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.jpa.CompanyTraitsJpaModel;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.jpa.PersonalTraitsJpaModel;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.jpa.SocialNetworksJpaModel;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.jpa.JobOfferJpaModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TalentJpaSpecification {

   /**
    * @param talentProfileId find by one TalentProfile id.
    */
    public static Specification<TalentJpaModel> byTalentProfileSpecification(final String talentProfileId) {
        return (root, query, cb) -> {
            final Join<Talent, TalentProfile> talentForTalentProfileJoin = root.join(
                "talentProfile",
                JoinType.LEFT
            );

            final TalentProfileJpaModel talentProfile = new TalentProfileJpaModel();
            talentProfile.setId(talentProfileId);

            return talentForTalentProfileJoin.in(talentProfile);
        };
    }

   /**
    * @param talentProfileIds find by a list of TalentProfile ids.
    */
    public static Specification<TalentJpaModel> byTalentProfileSpecification(final Collection<String> talentProfileIds) {
        return (root, query, cb) -> {
            final Join<Talent, TalentProfile> talentForTalentProfileJoin = root.join(
                "talentProfile",
                JoinType.LEFT
            );

            final List<TalentProfileJpaModel> list = new ArrayList<>();
            talentProfileIds.forEach(v -> {
                final TalentProfileJpaModel talentProfile = new TalentProfileJpaModel();
                talentProfile.setId(v);
                list.add(talentProfile);
            });

            return talentForTalentProfileJoin.in(list);
        };
    }

   /**
    * @param talentProfiles find by a list of TalentProfileJpaModel instances.
    */
    public static Specification<TalentJpaModel> byTalentProfileSpecification(final Iterable<TalentProfileJpaModel> talentProfiles) {
        return (root, query, cb) -> {
            final Join<Talent, TalentProfile> talentForTalentProfileJoin = root.join(
                "talentProfile",
                JoinType.LEFT
            );

            return talentForTalentProfileJoin.in(talentProfiles);
        };
    }
    
   /**
    * @param talentRoleId find by one TalentRole id.
    */
    public static Specification<TalentJpaModel> byTalentRoleSpecification(final String talentRoleId) {
        return (root, query, cb) -> {
            final Join<Talent, TalentRole> talentForTalentRoleJoin = root.join(
                "talentRole",
                JoinType.LEFT
            );

            final TalentRoleJpaModel talentRole = new TalentRoleJpaModel();
            talentRole.setId(talentRoleId);

            return talentForTalentRoleJoin.in(talentRole);
        };
    }

   /**
    * @param talentRoleIds find by a list of TalentRole ids.
    */
    public static Specification<TalentJpaModel> byTalentRoleSpecification(final Collection<String> talentRoleIds) {
        return (root, query, cb) -> {
            final Join<Talent, TalentRole> talentForTalentRoleJoin = root.join(
                "talentRole",
                JoinType.LEFT
            );

            final List<TalentRoleJpaModel> list = new ArrayList<>();
            talentRoleIds.forEach(v -> {
                final TalentRoleJpaModel talentRole = new TalentRoleJpaModel();
                talentRole.setId(v);
                list.add(talentRole);
            });

            return talentForTalentRoleJoin.in(list);
        };
    }

   /**
    * @param talentRoles find by a list of TalentRoleJpaModel instances.
    */
    public static Specification<TalentJpaModel> byTalentRoleSpecification(final Iterable<TalentRoleJpaModel> talentRoles) {
        return (root, query, cb) -> {
            final Join<Talent, TalentRole> talentForTalentRoleJoin = root.join(
                "talentRole",
                JoinType.LEFT
            );

            return talentForTalentRoleJoin.in(talentRoles);
        };
    }
    
   /**
    * @param countryId find by one Country id.
    */
    public static Specification<TalentJpaModel> byCountrySpecification(final String countryId) {
        return (root, query, cb) -> {
            final Join<Talent, Country> talentForCountryJoin = root.join(
                "country",
                JoinType.LEFT
            );

            final CountryJpaModel country = new CountryJpaModel();
            country.setId(countryId);

            return talentForCountryJoin.in(country);
        };
    }

   /**
    * @param countryIds find by a list of Country ids.
    */
    public static Specification<TalentJpaModel> byCountrySpecification(final Collection<String> countryIds) {
        return (root, query, cb) -> {
            final Join<Talent, Country> talentForCountryJoin = root.join(
                "country",
                JoinType.LEFT
            );

            final List<CountryJpaModel> list = new ArrayList<>();
            countryIds.forEach(v -> {
                final CountryJpaModel country = new CountryJpaModel();
                country.setId(v);
                list.add(country);
            });

            return talentForCountryJoin.in(list);
        };
    }

   /**
    * @param countries find by a list of CountryJpaModel instances.
    */
    public static Specification<TalentJpaModel> byCountrySpecification(final Iterable<CountryJpaModel> countries) {
        return (root, query, cb) -> {
            final Join<Talent, Country> talentForCountryJoin = root.join(
                "country",
                JoinType.LEFT
            );

            return talentForCountryJoin.in(countries);
        };
    }
    
   /**
    * @param cityId find by one City id.
    */
    public static Specification<TalentJpaModel> byCitySpecification(final String cityId) {
        return (root, query, cb) -> {
            final Join<Talent, City> talentForCityJoin = root.join(
                "city",
                JoinType.LEFT
            );

            final CityJpaModel city = new CityJpaModel();
            city.setId(cityId);

            return talentForCityJoin.in(city);
        };
    }

   /**
    * @param cityIds find by a list of City ids.
    */
    public static Specification<TalentJpaModel> byCitySpecification(final Collection<String> cityIds) {
        return (root, query, cb) -> {
            final Join<Talent, City> talentForCityJoin = root.join(
                "city",
                JoinType.LEFT
            );

            final List<CityJpaModel> list = new ArrayList<>();
            cityIds.forEach(v -> {
                final CityJpaModel city = new CityJpaModel();
                city.setId(v);
                list.add(city);
            });

            return talentForCityJoin.in(list);
        };
    }

   /**
    * @param cities find by a list of CityJpaModel instances.
    */
    public static Specification<TalentJpaModel> byCitySpecification(final Iterable<CityJpaModel> cities) {
        return (root, query, cb) -> {
            final Join<Talent, City> talentForCityJoin = root.join(
                "city",
                JoinType.LEFT
            );

            return talentForCityJoin.in(cities);
        };
    }
    
   /**
    * @param talentTitleId find by one TalentTitle id.
    */
    public static Specification<TalentJpaModel> byTalentTitleSpecification(final String talentTitleId) {
        return (root, query, cb) -> {
            final Join<Talent, TalentTitle> talentForTalentTitleJoin = root.join(
                "talentTitle",
                JoinType.LEFT
            );

            final TalentTitleJpaModel talentTitle = new TalentTitleJpaModel();
            talentTitle.setId(talentTitleId);

            return talentForTalentTitleJoin.in(talentTitle);
        };
    }

   /**
    * @param talentTitleIds find by a list of TalentTitle ids.
    */
    public static Specification<TalentJpaModel> byTalentTitleSpecification(final Collection<String> talentTitleIds) {
        return (root, query, cb) -> {
            final Join<Talent, TalentTitle> talentForTalentTitleJoin = root.join(
                "talentTitle",
                JoinType.LEFT
            );

            final List<TalentTitleJpaModel> list = new ArrayList<>();
            talentTitleIds.forEach(v -> {
                final TalentTitleJpaModel talentTitle = new TalentTitleJpaModel();
                talentTitle.setId(v);
                list.add(talentTitle);
            });

            return talentForTalentTitleJoin.in(list);
        };
    }

   /**
    * @param talentTitles find by a list of TalentTitleJpaModel instances.
    */
    public static Specification<TalentJpaModel> byTalentTitleSpecification(final Iterable<TalentTitleJpaModel> talentTitles) {
        return (root, query, cb) -> {
            final Join<Talent, TalentTitle> talentForTalentTitleJoin = root.join(
                "talentTitle",
                JoinType.LEFT
            );

            return talentForTalentTitleJoin.in(talentTitles);
        };
    }
    
   /**
    * @param talentExperienceId find by one TalentExperience id.
    */
    public static Specification<TalentJpaModel> byTalentExperienceSpecification(final String talentExperienceId) {
        return (root, query, cb) -> {
            final Join<Talent, TalentExperience> talentForTalentExperienceJoin = root.join(
                "talentExperience",
                JoinType.LEFT
            );

            final TalentExperienceJpaModel talentExperience = new TalentExperienceJpaModel();
            talentExperience.setId(talentExperienceId);

            return talentForTalentExperienceJoin.in(talentExperience);
        };
    }

   /**
    * @param talentExperienceIds find by a list of TalentExperience ids.
    */
    public static Specification<TalentJpaModel> byTalentExperienceSpecification(final Collection<String> talentExperienceIds) {
        return (root, query, cb) -> {
            final Join<Talent, TalentExperience> talentForTalentExperienceJoin = root.join(
                "talentExperience",
                JoinType.LEFT
            );

            final List<TalentExperienceJpaModel> list = new ArrayList<>();
            talentExperienceIds.forEach(v -> {
                final TalentExperienceJpaModel talentExperience = new TalentExperienceJpaModel();
                talentExperience.setId(v);
                list.add(talentExperience);
            });

            return talentForTalentExperienceJoin.in(list);
        };
    }

   /**
    * @param talentExperiences find by a list of TalentExperienceJpaModel instances.
    */
    public static Specification<TalentJpaModel> byTalentExperienceSpecification(final Iterable<TalentExperienceJpaModel> talentExperiences) {
        return (root, query, cb) -> {
            final Join<Talent, TalentExperience> talentForTalentExperienceJoin = root.join(
                "talentExperience",
                JoinType.LEFT
            );

            return talentForTalentExperienceJoin.in(talentExperiences);
        };
    }
    
   /**
    * @param workTypeId find by one WorkType id.
    */
    public static Specification<TalentJpaModel> byWorkTypeSpecification(final String workTypeId) {
        return (root, query, cb) -> {
            final Join<Talent, WorkType> talentForWorkTypeJoin = root.join(
                "workType",
                JoinType.LEFT
            );

            final WorkTypeJpaModel workType = new WorkTypeJpaModel();
            workType.setId(workTypeId);

            return talentForWorkTypeJoin.in(workType);
        };
    }

   /**
    * @param workTypeIds find by a list of WorkType ids.
    */
    public static Specification<TalentJpaModel> byWorkTypeSpecification(final Collection<String> workTypeIds) {
        return (root, query, cb) -> {
            final Join<Talent, WorkType> talentForWorkTypeJoin = root.join(
                "workType",
                JoinType.LEFT
            );

            final List<WorkTypeJpaModel> list = new ArrayList<>();
            workTypeIds.forEach(v -> {
                final WorkTypeJpaModel workType = new WorkTypeJpaModel();
                workType.setId(v);
                list.add(workType);
            });

            return talentForWorkTypeJoin.in(list);
        };
    }

   /**
    * @param workTypes find by a list of WorkTypeJpaModel instances.
    */
    public static Specification<TalentJpaModel> byWorkTypeSpecification(final Iterable<WorkTypeJpaModel> workTypes) {
        return (root, query, cb) -> {
            final Join<Talent, WorkType> talentForWorkTypeJoin = root.join(
                "workType",
                JoinType.LEFT
            );

            return talentForWorkTypeJoin.in(workTypes);
        };
    }
    
   /**
    * @param skillsId find by one Skills id.
    */
    public static Specification<TalentJpaModel> bySkillsSpecification(final String skillsId) {
        return (root, query, cb) -> {
            final Join<Talent, Skills> talentForSkillsJoin = root.join(
                "skills",
                JoinType.LEFT
            );

            final SkillsJpaModel skills = new SkillsJpaModel();
            skills.setId(skillsId);

            return talentForSkillsJoin.in(skills);
        };
    }

   /**
    * @param skillsIds find by a list of Skills ids.
    */
    public static Specification<TalentJpaModel> bySkillsSpecification(final Collection<String> skillsIds) {
        return (root, query, cb) -> {
            final Join<Talent, Skills> talentForSkillsJoin = root.join(
                "skills",
                JoinType.LEFT
            );

            final List<SkillsJpaModel> list = new ArrayList<>();
            skillsIds.forEach(v -> {
                final SkillsJpaModel skills = new SkillsJpaModel();
                skills.setId(v);
                list.add(skills);
            });

            return talentForSkillsJoin.in(list);
        };
    }

   /**
    * @param skills find by a list of SkillsJpaModel instances.
    */
    public static Specification<TalentJpaModel> bySkillsSpecification(final Iterable<SkillsJpaModel> skills) {
        return (root, query, cb) -> {
            final Join<Talent, Skills> talentForSkillsJoin = root.join(
                "skills",
                JoinType.LEFT
            );

            return talentForSkillsJoin.in(skills);
        };
    }
    
   /**
    * @param notableProjectsId find by one NotableProjects id.
    */
    public static Specification<TalentJpaModel> byNotableProjectsSpecification(final String notableProjectsId) {
        return (root, query, cb) -> {
            final Join<Talent, NotableProjects> talentForNotableProjectsJoin = root.join(
                "notableProjects",
                JoinType.LEFT
            );

            final NotableProjectsJpaModel notableProjects = new NotableProjectsJpaModel();
            notableProjects.setId(notableProjectsId);

            return talentForNotableProjectsJoin.in(notableProjects);
        };
    }

   /**
    * @param notableProjectsIds find by a list of NotableProjects ids.
    */
    public static Specification<TalentJpaModel> byNotableProjectsSpecification(final Collection<String> notableProjectsIds) {
        return (root, query, cb) -> {
            final Join<Talent, NotableProjects> talentForNotableProjectsJoin = root.join(
                "notableProjects",
                JoinType.LEFT
            );

            final List<NotableProjectsJpaModel> list = new ArrayList<>();
            notableProjectsIds.forEach(v -> {
                final NotableProjectsJpaModel notableProjects = new NotableProjectsJpaModel();
                notableProjects.setId(v);
                list.add(notableProjects);
            });

            return talentForNotableProjectsJoin.in(list);
        };
    }

   /**
    * @param notableProjects find by a list of NotableProjectsJpaModel instances.
    */
    public static Specification<TalentJpaModel> byNotableProjectsSpecification(final Iterable<NotableProjectsJpaModel> notableProjects) {
        return (root, query, cb) -> {
            final Join<Talent, NotableProjects> talentForNotableProjectsJoin = root.join(
                "notableProjects",
                JoinType.LEFT
            );

            return talentForNotableProjectsJoin.in(notableProjects);
        };
    }
    
   /**
    * @param companyTraitsId find by one CompanyTraits id.
    */
    public static Specification<TalentJpaModel> byCompanyTraitsSpecification(final String companyTraitsId) {
        return (root, query, cb) -> {
            final Join<Talent, CompanyTraits> talentForCompanyTraitsJoin = root.join(
                "companyTraits",
                JoinType.LEFT
            );

            final CompanyTraitsJpaModel companyTraits = new CompanyTraitsJpaModel();
            companyTraits.setId(companyTraitsId);

            return talentForCompanyTraitsJoin.in(companyTraits);
        };
    }

   /**
    * @param companyTraitsIds find by a list of CompanyTraits ids.
    */
    public static Specification<TalentJpaModel> byCompanyTraitsSpecification(final Collection<String> companyTraitsIds) {
        return (root, query, cb) -> {
            final Join<Talent, CompanyTraits> talentForCompanyTraitsJoin = root.join(
                "companyTraits",
                JoinType.LEFT
            );

            final List<CompanyTraitsJpaModel> list = new ArrayList<>();
            companyTraitsIds.forEach(v -> {
                final CompanyTraitsJpaModel companyTraits = new CompanyTraitsJpaModel();
                companyTraits.setId(v);
                list.add(companyTraits);
            });

            return talentForCompanyTraitsJoin.in(list);
        };
    }

   /**
    * @param companyTraits find by a list of CompanyTraitsJpaModel instances.
    */
    public static Specification<TalentJpaModel> byCompanyTraitsSpecification(final Iterable<CompanyTraitsJpaModel> companyTraits) {
        return (root, query, cb) -> {
            final Join<Talent, CompanyTraits> talentForCompanyTraitsJoin = root.join(
                "companyTraits",
                JoinType.LEFT
            );

            return talentForCompanyTraitsJoin.in(companyTraits);
        };
    }
    
   /**
    * @param personalTraitsId find by one PersonalTraits id.
    */
    public static Specification<TalentJpaModel> byPersonalTraitsSpecification(final String personalTraitsId) {
        return (root, query, cb) -> {
            final Join<Talent, PersonalTraits> talentForPersonalTraitsJoin = root.join(
                "personalTraits",
                JoinType.LEFT
            );

            final PersonalTraitsJpaModel personalTraits = new PersonalTraitsJpaModel();
            personalTraits.setId(personalTraitsId);

            return talentForPersonalTraitsJoin.in(personalTraits);
        };
    }

   /**
    * @param personalTraitsIds find by a list of PersonalTraits ids.
    */
    public static Specification<TalentJpaModel> byPersonalTraitsSpecification(final Collection<String> personalTraitsIds) {
        return (root, query, cb) -> {
            final Join<Talent, PersonalTraits> talentForPersonalTraitsJoin = root.join(
                "personalTraits",
                JoinType.LEFT
            );

            final List<PersonalTraitsJpaModel> list = new ArrayList<>();
            personalTraitsIds.forEach(v -> {
                final PersonalTraitsJpaModel personalTraits = new PersonalTraitsJpaModel();
                personalTraits.setId(v);
                list.add(personalTraits);
            });

            return talentForPersonalTraitsJoin.in(list);
        };
    }

   /**
    * @param personalTraits find by a list of PersonalTraitsJpaModel instances.
    */
    public static Specification<TalentJpaModel> byPersonalTraitsSpecification(final Iterable<PersonalTraitsJpaModel> personalTraits) {
        return (root, query, cb) -> {
            final Join<Talent, PersonalTraits> talentForPersonalTraitsJoin = root.join(
                "personalTraits",
                JoinType.LEFT
            );

            return talentForPersonalTraitsJoin.in(personalTraits);
        };
    }
    
   /**
    * @param socialNetworksId find by one SocialNetworks id.
    */
    public static Specification<TalentJpaModel> bySocialNetworksSpecification(final String socialNetworksId) {
        return (root, query, cb) -> {
            final Join<Talent, SocialNetworks> talentForSocialNetworksJoin = root.join(
                "socialNetworks",
                JoinType.LEFT
            );

            final SocialNetworksJpaModel socialNetworks = new SocialNetworksJpaModel();
            socialNetworks.setId(socialNetworksId);

            return talentForSocialNetworksJoin.in(socialNetworks);
        };
    }

   /**
    * @param socialNetworksIds find by a list of SocialNetworks ids.
    */
    public static Specification<TalentJpaModel> bySocialNetworksSpecification(final Collection<String> socialNetworksIds) {
        return (root, query, cb) -> {
            final Join<Talent, SocialNetworks> talentForSocialNetworksJoin = root.join(
                "socialNetworks",
                JoinType.LEFT
            );

            final List<SocialNetworksJpaModel> list = new ArrayList<>();
            socialNetworksIds.forEach(v -> {
                final SocialNetworksJpaModel socialNetworks = new SocialNetworksJpaModel();
                socialNetworks.setId(v);
                list.add(socialNetworks);
            });

            return talentForSocialNetworksJoin.in(list);
        };
    }

   /**
    * @param socialNetworks find by a list of SocialNetworksJpaModel instances.
    */
    public static Specification<TalentJpaModel> bySocialNetworksSpecification(final Iterable<SocialNetworksJpaModel> socialNetworks) {
        return (root, query, cb) -> {
            final Join<Talent, SocialNetworks> talentForSocialNetworksJoin = root.join(
                "socialNetworks",
                JoinType.LEFT
            );

            return talentForSocialNetworksJoin.in(socialNetworks);
        };
    }
    
   /**
    * @param jobOfferId find by one JobOffer id.
    */
    public static Specification<TalentJpaModel> byJobOfferSpecification(final String jobOfferId) {
        return (root, query, cb) -> {
            final Join<Talent, JobOffer> talentForJobOfferJoin = root.join(
                "jobOffer",
                JoinType.LEFT
            );

            final JobOfferJpaModel jobOffer = new JobOfferJpaModel();
            jobOffer.setId(jobOfferId);

            return talentForJobOfferJoin.in(jobOffer);
        };
    }

   /**
    * @param jobOfferIds find by a list of JobOffer ids.
    */
    public static Specification<TalentJpaModel> byJobOfferSpecification(final Collection<String> jobOfferIds) {
        return (root, query, cb) -> {
            final Join<Talent, JobOffer> talentForJobOfferJoin = root.join(
                "jobOffer",
                JoinType.LEFT
            );

            final List<JobOfferJpaModel> list = new ArrayList<>();
            jobOfferIds.forEach(v -> {
                final JobOfferJpaModel jobOffer = new JobOfferJpaModel();
                jobOffer.setId(v);
                list.add(jobOffer);
            });

            return talentForJobOfferJoin.in(list);
        };
    }

   /**
    * @param jobOffers find by a list of JobOfferJpaModel instances.
    */
    public static Specification<TalentJpaModel> byJobOfferSpecification(final Iterable<JobOfferJpaModel> jobOffers) {
        return (root, query, cb) -> {
            final Join<Talent, JobOffer> talentForJobOfferJoin = root.join(
                "jobOffer",
                JoinType.LEFT
            );

            return talentForJobOfferJoin.in(jobOffers);
        };
    }
    
}
