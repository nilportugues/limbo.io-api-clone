package io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.jpa.NotableProjectsJpaModel;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.jpa.NotableProjectsJpaRepository;
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
@Named("NotableProjectsJpaMapper")
public class NotableProjectsJpaMapper {

    @Inject private NotableProjectsJpaRepository notableProjectsJpaRepository;
    @Inject private TalentJpaRepository talentJpaRepository;
    @Inject private TalentJpaMapper talentJpaMapper;

    public NotableProjects toDomain(final NotableProjectsJpaModel model) {

        if (Optional.ofNullable(model).isPresent()) {
            try {
                final NotableProjects notableProjects = new NotableProjects();

                notableProjects.setId(model.getId());
                notableProjects.setTitle(model.getTitle());
                notableProjects.setDescription(model.getDescription());

                return notableProjects;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public NotableProjectsJpaModel toModel(final NotableProjects notableProjects) {

        if (Optional.ofNullable(notableProjects).isPresent()) {
            try {
                NotableProjectsJpaModel model = new NotableProjectsJpaModel();
                if (Optional.ofNullable(notableProjects.getId()).isPresent()) {
                    final NotableProjectsJpaModel dbModel = notableProjectsJpaRepository.findOne(notableProjects.getId());
                    if (null != dbModel) {
                        model = dbModel;
                    }
                }

                model.setId(notableProjects.getId());
                model.setTitle(notableProjects.getTitle());
                model.setDescription(notableProjects.getDescription());

                manyToOneTalentRelationship(notableProjects, model);

                return model;

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }
   /**
    * Sets up the many to one relationship between NotableProjects and Talent.
    *
    * @param notableProjects  The domain class representation for NotableProjects.
    * @param model  The model class representation for NotableProjects.
    */
    private void manyToOneTalentRelationship(final NotableProjects notableProjects, final NotableProjectsJpaModel model) {

        if (Optional.ofNullable(notableProjects.getTalent()).isPresent()) {
            final Talent talent = notableProjects.getTalent();
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