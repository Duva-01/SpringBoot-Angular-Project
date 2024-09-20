package ch.elca.backendninja.service;

import ch.elca.backendninja.model.GenderModel;
import ch.elca.backendninja.model.JobPositionModel;
import java.util.List;

public interface JobPositionService {

    public abstract List<JobPositionModel> getJobPositions();

    public abstract JobPositionModel getJobPosition(int id);

    public abstract JobPositionModel createJobPosition(JobPositionModel jobPosition);

    public abstract JobPositionModel updateJobPosition(JobPositionModel jobPosition);

    public abstract boolean removeJobPosition(int id);

    public abstract void saveJobPositions(List<JobPositionModel> jobPositions);
}
