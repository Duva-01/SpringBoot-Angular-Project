package ch.elca.backendninja.entity;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "street_name", nullable = false)
    private String street_name;

    @Column(name = "street_number", nullable = true)
    private int street_number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "main_address", nullable = false)
    private boolean main_address;

    public Address() {}

    public Address(int id, String street_name, int street_number, User user, boolean main_address) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isMain_address() {
        return main_address;
    }

    public void setMain_address(boolean main_address) {
        this.main_address = main_address;
    }
}
