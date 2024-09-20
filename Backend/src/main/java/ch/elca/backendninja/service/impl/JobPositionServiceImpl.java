package ch.elca.backendninja.service.impl;

import ch.elca.backendninja.converter.JobPositionConverter;
import ch.elca.backendninja.entity.JobPosition;
import ch.elca.backendninja.model.JobPositionModel;
import ch.elca.backendninja.repository.JobPositionRepository;
import ch.elca.backendninja.service.JobPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("jobPositionService")
public class JobPositionServiceImpl implements JobPositionService {

    @Autowired
    @Qualifier("jobPositionRepository")
    private JobPositionRepository jobPositionRepository;

    @Autowired
    @Qualifier("jobPositionConverter")
    private JobPositionConverter jobPositionConverter;

    @Override
    public List<JobPositionModel> getJobPositions() {

        List<JobPositionModel> jobPositionModelList = new ArrayList<>();

        for(JobPosition jobPosition : jobPositionRepository.findAll()){
            jobPositionModelList.add(jobPositionConverter.EntityToModel(jobPosition));
        }

        return jobPositionModelList;
    }

    @Override
    public JobPositionModel getJobPosition(int id) {
        return jobPositionConverter.EntityToModel(jobPositionRepository.findById(id).orElse(null));
    }

    @Override
    public JobPositionModel createJobPosition(JobPositionModel jobPosition) {

        JobPosition newJobPosition = jobPositionRepository.save(jobPositionConverter.ModelToEntity(jobPosition, true));

        return jobPositionConverter.EntityToModel(newJobPosition);
    }

    @Override
    public JobPositionModel updateJobPosition(JobPositionModel jobPosition) {

        JobPosition newJobPosition = jobPositionRepository.save(jobPositionConverter.ModelToEntity(jobPosition, false));

        return jobPositionConverter.EntityToModel(newJobPosition);
    }

    @Override
    public boolean removeJobPosition(int id) {

        JobPosition jobPosition = jobPositionRepository.findById(id).orElse(null);

        if(jobPosition != null){
            jobPositionRepository.delete(jobPosition);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void saveJobPositions(List<JobPositionModel> jobPositionModels) {

        List<JobPosition> existingJobPositions = jobPositionRepository.findAll();
        Map<Integer, JobPosition> existingJobPositionMap = new HashMap<>();

        for (JobPosition jobPosition : existingJobPositions) {
            existingJobPositionMap.put(jobPosition.getId(), jobPosition);
        }

        Set<Integer> incomingJobPositionIds = new HashSet<>();
        for (JobPositionModel jobPositionModel : jobPositionModels) {

            incomingJobPositionIds.add(jobPositionModel.getId());

            if (jobPositionModel.getId() != null && existingJobPositionMap.containsKey(jobPositionModel.getId())) {
                updateJobPosition(jobPositionModel);
            } else {
                JobPositionModel newJobPosition = new JobPositionModel();
                newJobPosition.setName(jobPositionModel.getName());
                createJobPosition(newJobPosition);
            }
        }

        for (Map.Entry<Integer, JobPosition> entry : existingJobPositionMap.entrySet()) {
            if (!incomingJobPositionIds.contains(entry.getKey())) {
                removeJobPosition(entry.getKey());
            }
        }
    }


}
