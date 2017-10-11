package io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.jpa.CompanyTraitsJpaModel;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
import javax.inject.Named;

/**
 * Spring Data JPA repository for the CompanyTraitsJpaModel entity.
 */
@Named("CompanyTraitsJpaRepository")
@SuppressWarnings("unused")
public interface CompanyTraitsJpaRepository extends JpaRepository<CompanyTraitsJpaModel,String>, JpaSpecificationExecutor {


    @Query("select distinct companyTraits from CompanyTraitsJpaModel companyTraits left join fetch companyTraits.talent where companyTraits.id =:id")
    CompanyTraitsJpaModel findOneWithTalent(@Param("id") String id);

    @Query("select companyTraits from CompanyTraitsJpaModel companyTraits where companyTraits.id =:id")
    CompanyTraitsJpaModel findOneWithEagerRelationships(@Param("id") String id);

}