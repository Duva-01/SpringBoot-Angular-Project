package ch.elca.backendninja.service.impl;

import ch.elca.backendninja.converter.AddressConverter;
import ch.elca.backendninja.entity.Address;
import ch.elca.backendninja.model.AddressModel;
import ch.elca.backendninja.repository.AddressRepository;
import ch.elca.backendninja.service.AddressService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("addressService")
public class AddressServiceImpl implements AddressService {

    private static final Log LOGGER = LogFactory.getLog(AddressServiceImpl.class);

    @Autowired
    @Qualifier("addressRepository")
    private AddressRepository addressRepository;

    @Autowired
    @Qualifier("addressConverter")
    private AddressConverter addressConverter;

    @Override
    public List<AddressModel> getUserAddresses(int id) {

        List<AddressModel> addresses = new ArrayList<>();

        for(Address address : addressRepository.findByUserId(id)) {
            addresses.add(addressConverter.EntityToModel(address));
        }

        return addresses;
    }

    @Override
    public AddressModel findAddressById(int id) {

        return addressConverter.EntityToModel(addressRepository.findById(id).orElse(null));
    }


    @Override
    public AddressModel updateAddress(AddressModel address) {
        try {
            if (checkMainAddress(address)) {
                LOGGER.error("Can't update address (main address) because main address already exists");
                return null;
            }

            Address updatedAddress = addressRepository.save(addressConverter.ModelToEntity(address));
            return addressConverter.EntityToModel(updatedAddress);

        } catch (Exception e) {
            LOGGER.error("Error while updating address", e);
            return null;
        }
    }

    @Override
    public List<AddressModel> updateAddresses(int userId, List<AddressModel> newAddresses) {

        List<AddressModel> userAddresses = getUserAddresses(userId);

        Map<Integer, AddressModel> currentAddressMap = userAddresses.stream()
                .collect(Collectors.toMap(AddressModel::getId, address -> address));

        List<AddressModel> updatedAddresses = new ArrayList<>();
        AddressModel currentMainAddress = userAddresses.stream().filter(AddressModel::isMain_address).findFirst().orElse(null);
        AddressModel newMainAddress = newAddresses.stream().filter(AddressModel::isMain_address).findFirst().orElse(null);

        if (currentMainAddress != null && newMainAddress != null && currentMainAddress.getId() != newMainAddress.getId()) {
            currentMainAddress.setMain_address(false);
            updateAddress(currentMainAddress);
        }

        for (AddressModel newAddress : newAddresses) {

            if (newAddress.getId() == 0 || !currentAddressMap.containsKey(newAddress.getId())) {
                AddressModel addedAddress = addAddress(newAddress);
                updatedAddresses.add(addedAddress);
            } else {
                AddressModel updatedAddress = updateAddress(newAddress);
                updatedAddresses.add(updatedAddress);
            }
        }

        for (AddressModel existingAddress : userAddresses) {
            if (newAddresses.stream().noneMatch(newAddr -> newAddr.getId() == existingAddress.getId())) {
                deleteAddress(existingAddress.getId());
            }
        }

        return updatedAddresses;
    }

    @Override
    public AddressModel addAddress(AddressModel addressModel) {

        try {

            if (checkMainAddress(addressModel)) {
                LOGGER.error("Can't create address (main address) because main address already exists");
                return null;
            }

            Address newAddress = addressRepository.save(addressConverter.ModelToEntity(addressModel));
            return addressConverter.EntityToModel(newAddress);

        } catch (Exception e) {
            LOGGER.error("Error while creating address", e);
            return null;
        }
    }


    @Override
    public boolean deleteAddress(int id) {

        Address address = addressRepository.findById(id).orElse(null);

        if(address != null){
            addressRepository.delete(address);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean deleteUserAddresses(int userId){

        try{
            List<AddressModel> addresses = getUserAddresses(userId);

            for(AddressModel address : addresses){
                deleteAddress(address.getId());
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("Error while deleting address", e);
            return false;
        }
    }

    public boolean checkMainAddress(AddressModel myAddressModel) {

        if(myAddressModel.isMain_address() && !getUserAddresses(myAddressModel.getUser().getId()).isEmpty()){

            for(AddressModel addressModel : getUserAddresses(myAddressModel.getUser().getId())) {

                if(addressModel.isMain_address() && myAddressModel.getId() != addressModel.getId()){

                    LOGGER.error("Cant create another main address");
                    return true;
                }
            }
        }

        return false;
    }
}
