package ch.elca.backendninja.converter;

import ch.elca.backendninja.entity.User;
import ch.elca.backendninja.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("userConverter")
public class UserConverter {

    @Autowired
    @Qualifier("genderConverter")
    private GenderConverter genderConverter;

    @Autowired
    @Qualifier("jobPositionConverter")
    private JobPositionConverter jobPositionConverter;


    public User ModelToEntity(UserModel userModel) {

        User user = new User();

        user.setId(userModel.getId());
        user.setUsername(userModel.getUsername());
        user.setPassword(userModel.getPassword());
        user.setCreation_datetime(userModel.getCreation_datetime());
        user.setGender(genderConverter.ModelToEntity(userModel.getGender(), false));
        user.setFirst_name(userModel.getFirst_name());
        user.setFirst_surname(userModel.getFirst_surname());
        user.setSecond_surname(userModel.getSecond_surname());
        user.setBirthdate(userModel.getBirthdate());
        user.setTime_of_breakfast(userModel.getTime_of_breakfast());

        if(userModel.getJob_position() != null){
            user.setJob_position(jobPositionConverter.ModelToEntity(userModel.getJob_position(), false));
        }
        else {
            user.setJob_position(null);
        }

        return user;
    }

    public UserModel EntityToModel(User user) {

        UserModel userModel = new UserModel();

        userModel.setId(user.getId());
        userModel.setUsername(user.getUsername());
        userModel.setPassword(user.getPassword());
        userModel.setCreation_datetime(user.getCreation_datetime());
        userModel.setGender(genderConverter.EntityToModel(user.getGender()));
        userModel.setFirst_name(user.getFirst_name());
        userModel.setFirst_surname(user.getFirst_surname());
        userModel.setSecond_surname(user.getSecond_surname());
        userModel.setBirthdate(user.getBirthdate());
        userModel.setTime_of_breakfast(user.getTime_of_breakfast());

        if(user.getJob_position() != null){
            userModel.setJob_position(jobPositionConverter.EntityToModel(user.getJob_position()));
        }
        else {
            userModel.setJob_position(null);
        }

        return userModel;
    }
}
