package io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.jpa.PersonalTraitsJpaModel;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.jpa.PersonalTraitsJpaRepository;
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
@Named("PersonalTraitsJpaMapper")
public class PersonalTraitsJpaMapper {

    @Inject private PersonalTraitsJpaRepository personalTraitsJpaRepository;
    @Inject private TalentJpaRepository talentJpaRepository;
    @Inject private TalentJpaMapper talentJpaMapper;

    public PersonalTraits toDomain(final PersonalTraitsJpaModel model) {

        if (Optional.ofNullable(model).isPresent()) {
            try {
                final PersonalTraits personalTraits = new PersonalTraits();

                personalTraits.setId(model.getId());
                personalTraits.setDescription(model.getDescription());

                return personalTraits;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public PersonalTraitsJpaModel toModel(final PersonalTraits personalTraits) {

        if (Optional.ofNullable(personalTraits).isPresent()) {
            try {
                PersonalTraitsJpaModel model = new PersonalTraitsJpaModel();
                if (Optional.ofNullable(personalTraits.getId()).isPresent()) {
                    final PersonalTraitsJpaModel dbModel = personalTraitsJpaRepository.findOne(personalTraits.getId());
                    if (null != dbModel) {
                        model = dbModel;
                    }
                }

                model.setId(personalTraits.getId());
                model.setDescription(personalTraits.getDescription());

                manyToOneTalentRelationship(personalTraits, model);

                return model;

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }
   /**
    * Sets up the many to one relationship between PersonalTraits and Talent.
    *
    * @param personalTraits  The domain class representation for PersonalTraits.
    * @param model  The model class representation for PersonalTraits.
    */
    private void manyToOneTalentRelationship(final PersonalTraits personalTraits, final PersonalTraitsJpaModel model) {

        if (Optional.ofNullable(personalTraits.getTalent()).isPresent()) {
            final Talent talent = personalTraits.getTalent();
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