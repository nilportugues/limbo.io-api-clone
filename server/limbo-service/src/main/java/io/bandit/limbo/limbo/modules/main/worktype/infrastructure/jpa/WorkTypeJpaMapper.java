package io.bandit.limbo.limbo.modules.main.worktype.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.jpa.WorkTypeJpaModel;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.jpa.WorkTypeJpaRepository;

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
@Named("WorkTypeJpaMapper")
public class WorkTypeJpaMapper {

    @Inject private WorkTypeJpaRepository workTypeJpaRepository;

    public WorkType toDomain(final WorkTypeJpaModel model) {

        if (Optional.ofNullable(model).isPresent()) {
            try {
                final WorkType workType = new WorkType();

                workType.setId(model.getId());
                workType.setWorkType(model.getWorkType());
                workType.setDescription(model.getDescription());

                return workType;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public WorkTypeJpaModel toModel(final WorkType workType) {

        if (Optional.ofNullable(workType).isPresent()) {
            try {
                WorkTypeJpaModel model = new WorkTypeJpaModel();
                if (Optional.ofNullable(workType.getId()).isPresent()) {
                    final WorkTypeJpaModel dbModel = workTypeJpaRepository.findOne(workType.getId());
                    if (null != dbModel) {
                        model = dbModel;
                    }
                }

                model.setId(workType.getId());
                model.setWorkType(workType.getWorkType());
                model.setDescription(workType.getDescription());


                return model;

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
//isManyToMany: 
//isManyToOne: 
//isOneToOne: 
//isAlone: 