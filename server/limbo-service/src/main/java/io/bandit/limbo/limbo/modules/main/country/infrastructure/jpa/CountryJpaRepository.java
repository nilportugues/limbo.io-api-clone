package io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaModel;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
import javax.inject.Named;

/**
 * Spring Data JPA repository for the CountryJpaModel entity.
 */
@Named("CountryJpaRepository")
@SuppressWarnings("unused")
public interface CountryJpaRepository extends JpaRepository<CountryJpaModel,String>, JpaSpecificationExecutor {


    @Query("select distinct country from CountryJpaModel country left join fetch country.cities where country.id =:id")
    CountryJpaModel findOneWithCities(@Param("id") String id);

    @Query("select country from CountryJpaModel country left join fetch country.cities where country.id =:id")
    CountryJpaModel findOneWithEagerRelationships(@Param("id") String id);

}