package ch.elca.backendninja.converter;

import ch.elca.backendninja.entity.Gender;
import ch.elca.backendninja.model.GenderModel;
import org.springframework.stereotype.Component;

@Component("genderConverter")
public class GenderConverter {

    public Gender ModelToEntity(GenderModel genderModel, boolean newEntity) {
        Gender gender = new Gender();

        if(!newEntity){
            gender.setId(genderModel.getId());
        }

        gender.setName(genderModel.getName());
        return gender;
    }

    public GenderModel EntityToModel(Gender gender) {
        GenderModel genderModel = new GenderModel();
        genderModel.setId(gender.getId());
        genderModel.setName(gender.getName());
        return genderModel;
    }
}
