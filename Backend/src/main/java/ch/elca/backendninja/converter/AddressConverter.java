package ch.elca.backendninja.converter;

import ch.elca.backendninja.entity.Address;
import ch.elca.backendninja.model.AddressModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("addressConverter")
public class AddressConverter {

    @Autowired
    @Qualifier("userConverter")
    private UserConverter userConverter;


    public Address ModelToEntity(AddressModel addressModel) {
        Address address = new Address();

        address.setId(addressModel.getId());
        address.setStreet_name(addressModel.getStreet_name());
        address.setStreet_number(addressModel.getStreet_number());
        address.setUser(userConverter.ModelToEntity(addressModel.getUser()));
        address.setMain_address(addressModel.isMain_address());
        return address;
    }

    public AddressModel EntityToModel(Address address) {
        AddressModel addressModel = new AddressModel();
        addressModel.setId(address.getId());
        addressModel.setStreet_name(address.getStreet_name());
        addressModel.setStreet_number(address.getStreet_number());
        addressModel.setUser(userConverter.EntityToModel(address.getUser()));
        addressModel.setMain_address(address.isMain_address());
        return addressModel;
    }
}
