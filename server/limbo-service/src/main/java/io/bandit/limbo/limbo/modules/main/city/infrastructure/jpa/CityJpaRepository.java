package io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaModel;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
import javax.inject.Named;

/**
 * Spring Data JPA repository for the CityJpaModel entity.
 */
@Named("CityJpaRepository")
@SuppressWarnings("unused")
public interface CityJpaRepository extends JpaRepository<CityJpaModel,String>, JpaSpecificationExecutor {


    @Query("select distinct city from CityJpaModel city left join fetch city.country where city.id =:id")
    CityJpaModel findOneWithCountry(@Param("id") String id);

    @Query("select city from CityJpaModel city where city.id =:id")
    CityJpaModel findOneWithEagerRelationships(@Param("id") String id);

}