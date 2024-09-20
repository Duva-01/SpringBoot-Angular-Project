package ch.elca.backendninja.model;

public class AddressModel {

    private int id;
    private String street_name;
    private int street_number;

    private UserModel user;
    private boolean main_address;

    public AddressModel() {}

    public AddressModel(int id, String street_name, int street_number, UserModel user, boolean main_address) {
        this.id = id;
        this.street_name = street_name;
        this.street_number = street_number;
        this.user = user;
        this.main_address = main_address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public int getStreet_number() {
        return street_number;
    }

    public void setStreet_number(int street_number) {
        this.street_number = street_number;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public boolean isMain_address() {
        return main_address;
    }

    public void setMain_address(boolean main_address) {
        this.main_address = main_address;
    }
}
