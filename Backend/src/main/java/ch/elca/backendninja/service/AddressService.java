package ch.elca.backendninja.service;

import ch.elca.backendninja.model.AddressModel;

import java.util.List;

public interface AddressService {

    public abstract List<AddressModel> getUserAddresses(int id);

    public abstract AddressModel findAddressById(int id);

    public abstract AddressModel addAddress(AddressModel user);

    public abstract AddressModel updateAddress(AddressModel user);

    public abstract List<AddressModel> updateAddresses(int userId, List<AddressModel> addresses);

    public abstract boolean deleteAddress(int id);

    public abstract boolean deleteUserAddresses(int userId);
}
