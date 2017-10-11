package io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaModel;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
import javax.inject.Named;

/**
 * Spring Data JPA repository for the TalentJpaModel entity.
 */
@Named("TalentJpaRepository")
@SuppressWarnings("unused")
public interface TalentJpaRepository extends JpaRepository<TalentJpaModel,String>, JpaSpecificationExecutor {


    @Query("select distinct talent from TalentJpaModel talent left join fetch talent.talentProfile where talent.id =:id")
    TalentJpaModel findOneWithTalentProfile(@Param("id") String id);

    @Query("select distinct talent from TalentJpaModel talent left join fetch talent.talentRole where talent.id =:id")
    TalentJpaModel findOneWithTalentRole(@Param("id") String id);

    @Query("select distinct talent from TalentJpaModel talent left join fetch talent.country where talent.id =:id")
    TalentJpaModel findOneWithCountry(@Param("id") String id);

    @Query("select distinct talent from TalentJpaModel talent left join fetch talent.city where talent.id =:id")
    TalentJpaModel findOneWithCity(@Param("id") String id);

    @Query("select distinct talent from TalentJpaModel talent left join fetch talent.talentTitle where talent.id =:id")
    TalentJpaModel findOneWithTalentTitle(@Param("id") String id);

    @Query("select distinct talent from TalentJpaModel talent left join fetch talent.talentExperience where talent.id =:id")
    TalentJpaModel findOneWithTalentExperience(@Param("id") String id);

    @Query("select distinct talent from TalentJpaModel talent left join fetch talent.workType where talent.id =:id")
    TalentJpaModel findOneWithWorkType(@Param("id") String id);

    @Query("select distinct talent from TalentJpaModel talent left join fetch talent.skills where talent.id =:id")
    TalentJpaModel findOneWithSkills(@Param("id") String id);

    @Query("select distinct talent from TalentJpaModel talent left join fetch talent.notableProjects where talent.id =:id")
    TalentJpaModel findOneWithNotableProjects(@Param("id") String id);

    @Query("select distinct talent from TalentJpaModel talent left join fetch talent.companyTraits where talent.id =:id")
    TalentJpaModel findOneWithCompanyTraits(@Param("id") String id);

    @Query("select distinct talent from TalentJpaModel talent left join fetch talent.personalTraits where talent.id =:id")
    TalentJpaModel findOneWithPersonalTraits(@Param("id") String id);

    @Query("select distinct talent from TalentJpaModel talent left join fetch talent.socialNetworks where talent.id =:id")
    TalentJpaModel findOneWithSocialNetworks(@Param("id") String id);

    @Query("select distinct talent from TalentJpaModel talent left join fetch talent.jobOffers where talent.id =:id")
    TalentJpaModel findOneWithJobOffers(@Param("id") String id);

    @Query("select talent from TalentJpaModel talent left join fetch talent.skills left join fetch talent.notableProjects left join fetch talent.companyTraits left join fetch talent.personalTraits left join fetch talent.socialNetworks left join fetch talent.jobOffers where talent.id =:id")
    TalentJpaModel findOneWithEagerRelationships(@Param("id") String id);

}