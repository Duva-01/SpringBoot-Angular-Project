package ch.elca.backendninja.service;

import ch.elca.backendninja.model.GenderModel;
import java.util.List;

public interface GenderService {

    public abstract List<GenderModel> getGenders();

    public abstract GenderModel getGender(int id);

    public abstract GenderModel createGender(GenderModel gender);

    public abstract GenderModel updateGender(GenderModel gender);

    public abstract boolean removeGender(int id);

    public abstract void saveGenders(List<GenderModel> genders);
}
