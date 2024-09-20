package ch.elca.backendninja.service.impl;

import ch.elca.backendninja.converter.UserConverter;
import ch.elca.backendninja.converter.UserImageConverter;
import ch.elca.backendninja.entity.UserImage;
import ch.elca.backendninja.model.AddressModel;
import ch.elca.backendninja.model.UserImageModel;
import ch.elca.backendninja.repository.UserImageRepository;
import ch.elca.backendninja.repository.UserRepository;
import ch.elca.backendninja.service.AddressService;
import ch.elca.backendninja.service.UserService;
import ch.elca.backendninja.entity.User;
import ch.elca.backendninja.model.UserModel;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Log LOGGER = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    @Qualifier("addressService")
    private AddressService addressService;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    @Qualifier("userImageRepository")
    private UserImageRepository userImageRepository;

    @Autowired
    @Qualifier("userConverter")
    private UserConverter userConverter;

    @Autowired
    @Qualifier("userImageConverter")
    private UserImageConverter userImageConverter;

    @Override
    public List<UserModel> getAllUsers() {

        List<UserModel> users = new ArrayList<UserModel>();

        for(User user : userRepository.findAll()){
            users.add(userConverter.EntityToModel(user));
        }

        return users;
    }

    @Override
    public UserModel findByUsername(String username) {

        User user = userRepository.findByUsername(username);

        if(user != null){
            return userConverter.EntityToModel(user);
        }
        else {
            return null;
        }
    }

    @Override
    public UserModel findUserById(int id) {

        return userConverter.EntityToModel(userRepository.findById(id).orElse(null));
    }

    @Override
    public UserModel addUser(UserModel user) {
        User myUser = userRepository.save(userConverter.ModelToEntity(user));

        return userConverter.EntityToModel(myUser);
    }

    @Override
    public UserModel updateUser(UserModel user, List<AddressModel> addresses) {

        User newUser = null;

        try{
            newUser = userRepository.save(userConverter.ModelToEntity(user));
            addressService.updateAddresses(user.getId(), addresses);
        }
        catch(Exception e){

            LOGGER.error("Error updating user", e);
        }

        return userConverter.EntityToModel(newUser);
    }

    @Override
    public boolean deleteUser(int id) {

        User user = userRepository.findById(id).orElse(null);

        if(user != null){

            removeImagesOfUser(user.getId());
            addressService.deleteUserAddresses(user.getId());
            userRepository.delete(user);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public List<UserImageModel> getAllUserImages(){

        List<UserImageModel> userImageModels = new ArrayList<UserImageModel>();

        for(UserImage userImage : userImageRepository.findAll()){
            userImageModels.add(userImageConverter.EntityToModel(userImage));
        }

        return userImageModels;
    }

    @Override
    public UserImageModel createUserImage(MultipartFile image, int userId) {

        try {

            String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
            UserModel userModel = findUserById(userId);

            UserImageModel userImageModel = new UserImageModel(base64Image, userModel);

            UserImage newUserImage = userImageRepository.save(userImageConverter.ModelToEntity(userImageModel, true));
            return userImageConverter.EntityToModel(newUserImage);

        }catch(Exception e){

            LOGGER.error("Error creating user image", e);
            return null;
        }
    }

    @Override
    public UserImageModel updateUserImage(MultipartFile image, int userImageId) {
        try {
            String base64Image = Base64.getEncoder().encodeToString(image.getBytes());

            Optional<UserImage> existingUserImageOptional = userImageRepository.findById(userImageId);
            if (existingUserImageOptional.isPresent()) {
                UserImage existingUserImage = existingUserImageOptional.get();
                existingUserImage.setImage(base64Image);

                UserImage updatedUserImage = userImageRepository.save(existingUserImage);
                return userImageConverter.EntityToModel(updatedUserImage);
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("Error updating user image", e);
            return null;
        }
    }

    @Override
    public UserImageModel getUserImage(int id) {

        UserImageModel userImageModel = userImageConverter.EntityToModel(userImageRepository.findById(id).orElse(null));

        return userImageModel;
    }

    @Override
    public boolean removeUserImage(int id) {

        UserImage userImage = userImageRepository.findById(id).orElse(null);

        if(userImage != null){
            userImageRepository.delete(userImage);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean removeImagesOfUser(int userId) {
        try {
            List<UserImage> userImages = userImageRepository.findByUserId(userId);

            if (userImages.isEmpty()) {
                LOGGER.info("No images found for user with ID: " + userId);
                return true;
            }

            for (UserImage userImage : userImages) {
                userImageRepository.delete(userImage);
            }

            LOGGER.info("Successfully removed images for user with ID: " + userId);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error while removing images for user with ID: " + userId, e);
            return false;
        }
    }
}
