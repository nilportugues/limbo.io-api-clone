package io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersonalTraitsJpaSpecification {

   /**
    * @param talentId find by one Talent id.
    */
    public static Specification<PersonalTraitsJpaModel> byTalentSpecification(final String talentId) {
        return (root, query, cb) -> {
            final Join<PersonalTraits, Talent> personalTraitsForTalentJoin = root.join(
                "talent",
                JoinType.LEFT
            );

            final TalentJpaModel talent = new TalentJpaModel();
            talent.setId(talentId);

            return personalTraitsForTalentJoin.in(talent);
        };
    }

   /**
    * @param talentIds find by a list of Talent ids.
    */
    public static Specification<PersonalTraitsJpaModel> byTalentSpecification(final Collection<String> talentIds) {
        return (root, query, cb) -> {
            final Join<PersonalTraits, Talent> personalTraitsForTalentJoin = root.join(
                "talent",
                JoinType.LEFT
            );

            final List<TalentJpaModel> list = new ArrayList<>();
            talentIds.forEach(v -> {
                final TalentJpaModel talent = new TalentJpaModel();
                talent.setId(v);
                list.add(talent);
            });

            return personalTraitsForTalentJoin.in(list);
        };
    }

   /**
    * @param talents find by a list of TalentJpaModel instances.
    */
    public static Specification<PersonalTraitsJpaModel> byTalentSpecification(final Iterable<TalentJpaModel> talents) {
        return (root, query, cb) -> {
            final Join<PersonalTraits, Talent> personalTraitsForTalentJoin = root.join(
                "talent",
                JoinType.LEFT
            );

            return personalTraitsForTalentJoin.in(talents);
        };
    }
    
}
