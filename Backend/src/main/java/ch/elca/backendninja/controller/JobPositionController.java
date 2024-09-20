package ch.elca.backendninja.controller;


import ch.elca.backendninja.model.GenderModel;
import ch.elca.backendninja.model.JobPositionModel;
import ch.elca.backendninja.service.JobPositionService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("jobPositionController")
@RequestMapping("/jobpositions")
public class JobPositionController {

    private static final Log LOGGER = LogFactory.getLog(JobPositionController.class);

    @Autowired
    @Qualifier("jobPositionService")
    private JobPositionService jobPositionService;

    @GetMapping("/getjobpositions")
    public ResponseEntity<List<JobPositionModel>> getJobPositions() {

        return new ResponseEntity<List<JobPositionModel>>(jobPositionService.getJobPositions(), HttpStatus.OK);
    }

    @GetMapping("/getjobposition")
    public ResponseEntity<JobPositionModel> getJobPosition(@RequestParam int id) {
        return new ResponseEntity<>(jobPositionService.getJobPosition(id), HttpStatus.OK);
    }

    @PostMapping("/createjobposition")
    public ResponseEntity<JobPositionModel> createJobPosition(@RequestBody JobPositionModel jobPosition) {
        return new ResponseEntity<>(jobPositionService.createJobPosition(jobPosition), HttpStatus.CREATED);
    }

    @PutMapping("/updatejobposition")
    public ResponseEntity<JobPositionModel> updateJobPosition(@RequestBody JobPositionModel jobPosition) {
        return new ResponseEntity<>(jobPositionService.updateJobPosition(jobPosition), HttpStatus.OK);
    }

    @DeleteMapping("/removejobposition")
    public boolean removeJobPosition(@RequestParam int id) {
        return jobPositionService.removeJobPosition(id);
    }

    @PostMapping("/savejobpositions")
    public ResponseEntity<?> saveJobPositions(@RequestBody List<JobPositionModel> jobPositions) {
        try {
            LOGGER.info("Saving " + jobPositions.size() + " job positions");
            jobPositionService.saveJobPositions(jobPositions);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error saving job positions", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
