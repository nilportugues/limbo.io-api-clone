package io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompanyTraitsJpaSpecification {

   /**
    * @param talentId find by one Talent id.
    */
    public static Specification<CompanyTraitsJpaModel> byTalentSpecification(final String talentId) {
        return (root, query, cb) -> {
            final Join<CompanyTraits, Talent> companyTraitsForTalentJoin = root.join(
                "talent",
                JoinType.LEFT
            );

            final TalentJpaModel talent = new TalentJpaModel();
            talent.setId(talentId);

            return companyTraitsForTalentJoin.in(talent);
        };
    }

   /**
    * @param talentIds find by a list of Talent ids.
    */
    public static Specification<CompanyTraitsJpaModel> byTalentSpecification(final Collection<String> talentIds) {
        return (root, query, cb) -> {
            final Join<CompanyTraits, Talent> companyTraitsForTalentJoin = root.join(
                "talent",
                JoinType.LEFT
            );

            final List<TalentJpaModel> list = new ArrayList<>();
            talentIds.forEach(v -> {
                final TalentJpaModel talent = new TalentJpaModel();
                talent.setId(v);
                list.add(talent);
            });

            return companyTraitsForTalentJoin.in(list);
        };
    }

   /**
    * @param talents find by a list of TalentJpaModel instances.
    */
    public static Specification<CompanyTraitsJpaModel> byTalentSpecification(final Iterable<TalentJpaModel> talents) {
        return (root, query, cb) -> {
            final Join<CompanyTraits, Talent> companyTraitsForTalentJoin = root.join(
                "talent",
                JoinType.LEFT
            );

            return companyTraitsForTalentJoin.in(talents);
        };
    }
    
}
