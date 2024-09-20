package ch.elca.backendninja.converter;

import ch.elca.backendninja.entity.Address;
import ch.elca.backendninja.entity.UserImage;
import ch.elca.backendninja.model.AddressModel;
import ch.elca.backendninja.model.UserImageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("userImageConverter")
public class UserImageConverter {

    @Autowired
    @Qualifier("userConverter")
    private UserConverter userConverter;

    public UserImage ModelToEntity(UserImageModel userImageModel, boolean newEntity) {

        UserImage userImage = new UserImage();

        if(!newEntity){
            userImage.setId(userImageModel.getId());
        }

        userImage.setUser(userConverter.ModelToEntity(userImageModel.getUser()));
        userImage.setImage(userImageModel.getImage());

        return userImage;
    }

    public UserImageModel EntityToModel(UserImage userImage) {

        UserImageModel userImageModel = new UserImageModel();

        userImageModel.setId(userImage.getId());
        userImageModel.setImage(userImage.getImage());
        userImageModel.setUser(userConverter.EntityToModel(userImage.getUser()));

        return userImageModel;
    }
}
