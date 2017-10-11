package io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.jpa.PersonalTraitsJpaModel;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
import javax.inject.Named;

/**
 * Spring Data JPA repository for the PersonalTraitsJpaModel entity.
 */
@Named("PersonalTraitsJpaRepository")
@SuppressWarnings("unused")
public interface PersonalTraitsJpaRepository extends JpaRepository<PersonalTraitsJpaModel,String>, JpaSpecificationExecutor {


    @Query("select distinct personalTraits from PersonalTraitsJpaModel personalTraits left join fetch personalTraits.talent where personalTraits.id =:id")
    PersonalTraitsJpaModel findOneWithTalent(@Param("id") String id);

    @Query("select personalTraits from PersonalTraitsJpaModel personalTraits where personalTraits.id =:id")
    PersonalTraitsJpaModel findOneWithEagerRelationships(@Param("id") String id);

}