package ch.elca.backendninja.model;
import java.util.List;

public class UpdateUserRequestModel {

    private UserModel user;
    private List<AddressModel> addresses;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<AddressModel> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressModel> addresses) {
        this.addresses = addresses;
    }
}
