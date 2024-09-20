package ch.elca.backendninja.converter;

import ch.elca.backendninja.entity.JobPosition;
import ch.elca.backendninja.model.JobPositionModel;
import org.springframework.stereotype.Component;

@Component("jobPositionConverter")
public class JobPositionConverter {

    public JobPosition ModelToEntity(JobPositionModel jobPositionModel, boolean newEntity) {

        JobPosition jobPosition = new JobPosition();

        if(!newEntity){
            jobPosition.setId(jobPositionModel.getId());
        }


        jobPosition.setName(jobPositionModel.getName());
        return jobPosition;
    }

    public JobPositionModel EntityToModel(JobPosition jobPosition) {
        JobPositionModel jobPositionModel = new JobPositionModel();
        jobPositionModel.setId(jobPosition.getId());
        jobPositionModel.setName(jobPosition.getName());
        return jobPositionModel;
    }
}
