package ch.elca.backendninja.model;

import ch.elca.backendninja.entity.User;

import javax.persistence.*;

public class UserImageModel {


    private int id;
    private String image;
    private UserModel user;

    public UserImageModel() {}

    public UserImageModel(String image, UserModel user) {
        this.image = image;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
