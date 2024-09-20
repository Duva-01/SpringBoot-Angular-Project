package ch.elca.backendninja.controller;

import ch.elca.backendninja.model.AddressModel;
import ch.elca.backendninja.service.AddressService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("addressController")
@RequestMapping("/addresses")
public class AddressController {

    private static final Log LOGGER = LogFactory.getLog(AddressController.class);

    @Autowired
    @Qualifier("addressService")
    private AddressService addressService;


    @GetMapping("/getaddresses")
    public ResponseEntity<List<AddressModel>> getAddresses(@RequestParam int id) {
        return new ResponseEntity<List<AddressModel>>(addressService.getUserAddresses(id), HttpStatus.OK);
    }

    @GetMapping("/getaddress")
    public ResponseEntity<AddressModel> getAddress(@RequestParam int id) {
        return new ResponseEntity<AddressModel>(addressService.findAddressById(id), HttpStatus.OK);
    }

    @PostMapping("/createaddress")
    public ResponseEntity<AddressModel> createAddress(@RequestBody AddressModel address) {

        return new ResponseEntity<>(addressService.addAddress(address), HttpStatus.CREATED);
    }

    @PutMapping("/updateaddress")
    public ResponseEntity<AddressModel> updateAddress(@RequestBody AddressModel address) {

        return new ResponseEntity<>(addressService.updateAddress(address), HttpStatus.OK);
    }

    @DeleteMapping("/removeAddress")
    public boolean removeAddress(@RequestParam int id) {

        return addressService.deleteAddress(id);
    }

}
