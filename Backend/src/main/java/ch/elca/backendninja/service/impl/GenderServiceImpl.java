package ch.elca.backendninja.service.impl;

import ch.elca.backendninja.controller.GenderController;
import ch.elca.backendninja.converter.GenderConverter;
import ch.elca.backendninja.entity.Gender;
import ch.elca.backendninja.entity.User;
import ch.elca.backendninja.model.GenderModel;
import ch.elca.backendninja.repository.GenderRepository;
import ch.elca.backendninja.service.GenderService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("genderService")
public class GenderServiceImpl implements GenderService {

    private static final Log LOGGER = LogFactory.getLog(GenderServiceImpl.class);

    @Autowired
    @Qualifier("genderRepository")
    private GenderRepository genderRepository;

    @Autowired
    @Qualifier("genderConverter")
    private GenderConverter genderConverter;

    @Override
    public List<GenderModel> getGenders() {

        List<GenderModel> genderList = new ArrayList<>();

        for(Gender gender : genderRepository.findAll()) {
            genderList.add(genderConverter.EntityToModel(gender));
        }

        return genderList;
    }

    @Override
    public GenderModel getGender(int id) {

        return genderConverter.EntityToModel(genderRepository.findById(id).orElse(null));
    }

    @Override
    public GenderModel createGender(GenderModel gender) {

        Gender newGender= genderRepository.save(genderConverter.ModelToEntity(gender, true));
        LOGGER.error("Intento crear el gender: " + newGender.getName());
        return genderConverter.EntityToModel(newGender);
    }

    @Override
    public GenderModel updateGender(GenderModel gender) {

        Gender newGender= genderRepository.save(genderConverter.ModelToEntity(gender, false));
        return genderConverter.EntityToModel(newGender);
    }

    @Override
    public boolean removeGender(int id) {

        Gender gender = genderRepository.findById(id).orElse(null);

        if(gender != null){
            genderRepository.delete(gender);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void saveGenders(List<GenderModel> genders) {

        List<Gender> existingGenders = genderRepository.findAll();
        Map<Integer, Gender> existingGenderMap = new HashMap<>();

        for (Gender gender : existingGenders) {
            existingGenderMap.put(gender.getId(), gender);
        }

        Set<Integer> incomingGenderIds = new HashSet<>();
        for (GenderModel genderModel : genders) {

            incomingGenderIds.add(genderModel.getId());

            if (genderModel.getId() != null && existingGenderMap.containsKey(genderModel.getId())) {
                updateGender(genderModel);
            } else {
                GenderModel newGender = new GenderModel();
                newGender.setName(genderModel.getName());
                createGender(newGender);
            }
        }

        for (Map.Entry<Integer, Gender> entry : existingGenderMap.entrySet()) {
            if (!incomingGenderIds.contains(entry.getKey())) {
                removeGender(entry.getKey());
            }
        }
    }
}
