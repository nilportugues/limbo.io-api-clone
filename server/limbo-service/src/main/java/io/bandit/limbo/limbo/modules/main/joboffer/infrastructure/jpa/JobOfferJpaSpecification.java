package io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JobOfferJpaSpecification {

   /**
    * @param talentId find by one Talent id.
    */
    public static Specification<JobOfferJpaModel> byTalentSpecification(final String talentId) {
        return (root, query, cb) -> {
            final Join<JobOffer, Talent> jobOfferForTalentJoin = root.join(
                "talent",
                JoinType.LEFT
            );

            final TalentJpaModel talent = new TalentJpaModel();
            talent.setId(talentId);

            return jobOfferForTalentJoin.in(talent);
        };
    }

   /**
    * @param talentIds find by a list of Talent ids.
    */
    public static Specification<JobOfferJpaModel> byTalentSpecification(final Collection<String> talentIds) {
        return (root, query, cb) -> {
            final Join<JobOffer, Talent> jobOfferForTalentJoin = root.join(
                "talent",
                JoinType.LEFT
            );

            final List<TalentJpaModel> list = new ArrayList<>();
            talentIds.forEach(v -> {
                final TalentJpaModel talent = new TalentJpaModel();
                talent.setId(v);
                list.add(talent);
            });

            return jobOfferForTalentJoin.in(list);
        };
    }

   /**
    * @param talents find by a list of TalentJpaModel instances.
    */
    public static Specification<JobOfferJpaModel> byTalentSpecification(final Iterable<TalentJpaModel> talents) {
        return (root, query, cb) -> {
            final Join<JobOffer, Talent> jobOfferForTalentJoin = root.join(
                "talent",
                JoinType.LEFT
            );

            return jobOfferForTalentJoin.in(talents);
        };
    }
    
}
