package io.bandit.limbo.limbo.modules.main.skills.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SkillsJpaSpecification {

   /**
    * @param talentId find by one Talent id.
    */
    public static Specification<SkillsJpaModel> byTalentSpecification(final String talentId) {
        return (root, query, cb) -> {
            final Join<Skills, Talent> skillsForTalentJoin = root.join(
                "talent",
                JoinType.LEFT
            );

            final TalentJpaModel talent = new TalentJpaModel();
            talent.setId(talentId);

            return skillsForTalentJoin.in(talent);
        };
    }

   /**
    * @param talentIds find by a list of Talent ids.
    */
    public static Specification<SkillsJpaModel> byTalentSpecification(final Collection<String> talentIds) {
        return (root, query, cb) -> {
            final Join<Skills, Talent> skillsForTalentJoin = root.join(
                "talent",
                JoinType.LEFT
            );

            final List<TalentJpaModel> list = new ArrayList<>();
            talentIds.forEach(v -> {
                final TalentJpaModel talent = new TalentJpaModel();
                talent.setId(v);
                list.add(talent);
            });

            return skillsForTalentJoin.in(list);
        };
    }

   /**
    * @param talents find by a list of TalentJpaModel instances.
    */
    public static Specification<SkillsJpaModel> byTalentSpecification(final Iterable<TalentJpaModel> talents) {
        return (root, query, cb) -> {
            final Join<Skills, Talent> skillsForTalentJoin = root.join(
                "talent",
                JoinType.LEFT
            );

            return skillsForTalentJoin.in(talents);
        };
    }
    
}
