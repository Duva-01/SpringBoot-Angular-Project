package ch.elca.backendninja.controller;


import ch.elca.backendninja.converter.UserConverter;
import ch.elca.backendninja.model.*;
import ch.elca.backendninja.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

@RestController("userController")
@RequestMapping("/users")
public class UserController {

    private static final Log LOGGER = LogFactory.getLog(UserController.class);

    @Autowired
    @Qualifier("userService")
    private UserService userService;
    @Autowired
    private UserConverter userConverter;

    @PostMapping("/login")
    public ResponseEntity<UserModel> doLogin(@RequestBody LoginRequest loginRequest) {

        LOGGER.info("PETICION DE LOGIN -- PARAMS: " + loginRequest.getUsername() + ", " + loginRequest.getPassword());

        UserModel userModel = userService.findByUsername(loginRequest.getUsername());

        if (userModel != null && userModel.getPassword().equals(loginRequest.getPassword())) {
            return new ResponseEntity<>(userModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/getusers")
    public ResponseEntity<List<UserModel>> getUsers() {

        return new ResponseEntity<List<UserModel>>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/getuser")
    public ResponseEntity<UserModel> getUser(@RequestParam(name = "id", required = true) int id) {

        UserModel userModel = userService.findUserById(id);

        return new ResponseEntity<UserModel>(userModel, HttpStatus.OK);
    }

    @PostMapping("/createuser")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) {
        LOGGER.info("PETICION DE CREATE USER -- PARAMS: " + userModel);

        UserModel newUserModel = userService.addUser(userModel);
        return new ResponseEntity<UserModel>(newUserModel, HttpStatus.OK);
    }

    @PutMapping("/updateuser")
    public ResponseEntity<UserModel> updateUser(@RequestBody UpdateUserRequestModel updateRequest) {

        LOGGER.info("PETICION DE UPDATE USER -- PARAMS: " + updateRequest.getUser() + ", addresses: " + updateRequest.getAddresses());

        UserModel newUserModel = userService.updateUser(updateRequest.getUser(), updateRequest.getAddresses());
        return new ResponseEntity<UserModel>(newUserModel, HttpStatus.OK);
    }

    @DeleteMapping("/deleteuser")
    public boolean deleteUser(@RequestParam int id) {
        LOGGER.info("PETICION DE DELETE USER -- PARAMS: " + id);

        return userService.deleteUser(id);
    }

    @GetMapping("/getuserimages")
    public ResponseEntity<List<UserImageModel>> getAllUserImage() {
        return new ResponseEntity<List<UserImageModel>>(userService.getAllUserImages(), HttpStatus.OK);
    }

    @PostMapping("/createuserimage")
    public ResponseEntity<UserImageModel> createUserImage(@RequestParam("image") MultipartFile image, @RequestParam("userId") int userId) {

        LOGGER.info("PETICION DE CREATE IMAGE -- PARAMS: " + image.getOriginalFilename() + ", userId: " + userId);

        UserImageModel newUserImageModel = userService.createUserImage(image, userId);
        return new ResponseEntity<>(newUserImageModel, HttpStatus.OK);
    }

    @PutMapping("/updateuserimage")
    public ResponseEntity<UserImageModel> updateUserImage(@RequestParam("image") MultipartFile image, @RequestParam("userImageId") int userImageId) {
        LOGGER.info("PETICION DE UPDATE IMAGE -- PARAMS: " + image.getOriginalFilename() + ", userImageId: " + userImageId);

        UserImageModel updatedUserImageModel = userService.updateUserImage(image, userImageId);
        return new ResponseEntity<>(updatedUserImageModel, HttpStatus.OK);
    }


    @GetMapping("/getuserimage")
    public ResponseEntity<UserImageModel> getUserImage(@RequestParam(name = "id", required = true) int id) {

        LOGGER.info("PETICION DE GET IMAGE -- PARAMS: " + id);
        UserImageModel userImageModel = userService.getUserImage(id);

        return new ResponseEntity<UserImageModel>(userImageModel, HttpStatus.OK);
    }

    @DeleteMapping("/deleteuserimage")
    public boolean deleteUserImage(@RequestParam(name = "id", required = true) int id) {
        LOGGER.info("PETICION DE DELETE USER IMAGE -- PARAMS: " + id);

        return userService.removeUserImage(id);
    }

}
