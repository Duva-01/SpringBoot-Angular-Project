package ch.elca.backendninja.controller;

import ch.elca.backendninja.model.GenderModel;
import ch.elca.backendninja.service.GenderService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("genderController")
@RequestMapping("/genders")
public class GenderController {

    private static final Log LOGGER = LogFactory.getLog(GenderController.class);

    @Autowired
    @Qualifier("genderService")
    private GenderService genderService;

    @GetMapping("/getgenders")
    public ResponseEntity<List<GenderModel>> getGenders() {

        return new ResponseEntity<List<GenderModel>>(genderService.getGenders(), HttpStatus.OK);
    }

    @GetMapping("/getgender")
    public ResponseEntity<GenderModel> getGender(@RequestParam int id) {

        return new ResponseEntity<>(genderService.getGender(id), HttpStatus.OK);
    }

    @PostMapping("/creategender")
    public ResponseEntity<GenderModel> createGender(@RequestBody GenderModel gender) {
        return new ResponseEntity<>(genderService.createGender(gender), HttpStatus.OK);
    }

    @PutMapping("/updategender")
    public ResponseEntity<GenderModel> updateGender(@RequestBody GenderModel gender) {
        return new ResponseEntity<>(genderService.updateGender(gender), HttpStatus.OK);
    }

    @DeleteMapping("/removegender")
    public boolean removeGender(@RequestParam int id) {
        return genderService.removeGender(id);
    }

    @PostMapping("/savegenders")
    public ResponseEntity<?> saveGenders(@RequestBody List<GenderModel> genders) {
        try {

            genderService.saveGenders(genders);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error saving genders", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
