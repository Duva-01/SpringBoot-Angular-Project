package ch.elca.backendninja.service;

import ch.elca.backendninja.model.AddressModel;
import ch.elca.backendninja.model.GenderModel;
import ch.elca.backendninja.model.UserImageModel;
import ch.elca.backendninja.model.UserModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    public abstract List<UserModel> getAllUsers();

    public abstract UserModel findUserById(int id);

    public abstract UserModel findByUsername(String username);

    public abstract UserModel addUser(UserModel user);

    public abstract UserModel updateUser(UserModel user, List<AddressModel> addresses);

    public abstract boolean deleteUser(int id);

    public abstract List<UserImageModel> getAllUserImages();

    public abstract UserImageModel createUserImage(MultipartFile image, int userId);

    public abstract UserImageModel updateUserImage(MultipartFile image, int userId);

    public abstract UserImageModel getUserImage(int id);

    public abstract boolean removeUserImage(int id);

    public abstract boolean removeImagesOfUser(int userId);

}
