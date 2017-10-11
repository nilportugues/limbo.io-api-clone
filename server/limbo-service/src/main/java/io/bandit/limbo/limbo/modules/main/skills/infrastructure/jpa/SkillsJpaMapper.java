package io.bandit.limbo.limbo.modules.main.skills.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.jpa.SkillsJpaModel;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.jpa.SkillsJpaRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaMapper;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaModel;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaRepository;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaMapper;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@Named("SkillsJpaMapper")
public class SkillsJpaMapper {

    @Inject private SkillsJpaRepository skillsJpaRepository;
    @Inject private TalentJpaRepository talentJpaRepository;
    @Inject private TalentJpaMapper talentJpaMapper;

    public Skills toDomain(final SkillsJpaModel model) {

        if (Optional.ofNullable(model).isPresent()) {
            try {
                final Skills skills = new Skills();

                skills.setId(model.getId());
                skills.setSkill(model.getSkill());

                return skills;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public SkillsJpaModel toModel(final Skills skills) {

        if (Optional.ofNullable(skills).isPresent()) {
            try {
                SkillsJpaModel model = new SkillsJpaModel();
                if (Optional.ofNullable(skills.getId()).isPresent()) {
                    final SkillsJpaModel dbModel = skillsJpaRepository.findOne(skills.getId());
                    if (null != dbModel) {
                        model = dbModel;
                    }
                }

                model.setId(skills.getId());
                model.setSkill(skills.getSkill());

                manyToOneTalentRelationship(skills, model);

                return model;

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }
   /**
    * Sets up the many to one relationship between Skills and Talent.
    *
    * @param skills  The domain class representation for Skills.
    * @param model  The model class representation for Skills.
    */
    private void manyToOneTalentRelationship(final Skills skills, final SkillsJpaModel model) {

        if (Optional.ofNullable(skills.getTalent()).isPresent()) {
            final Talent talent = skills.getTalent();
            final String id = talent.getId();

            TalentJpaModel talentModel = null;
            if (Optional.ofNullable(talent.getId()).isPresent()) {
                talentModel = talentJpaRepository.findOne(id);
            }

            if (Objects.equals(model.getTalent(), talentModel)
                && Optional.ofNullable(model.getTalent()).isPresent()) {
                return;
            }

            if (!Optional.ofNullable(talentModel).isPresent()) {
                talentModel = talentJpaMapper.toModel(talent);
            }

            if (null != model.getTalent()) {
                talentJpaRepository.delete(model.getTalent());
            }

            model.setTalent(talentModel);
        }
    }

}
//isManyToMany: 
//isManyToOne: true
//isOneToOne: 
//isAlone: 