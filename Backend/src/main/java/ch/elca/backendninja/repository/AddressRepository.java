package ch.elca.backendninja.repository;

import ch.elca.backendninja.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("addressRepository")
public interface AddressRepository extends JpaRepository<Address, Serializable> {

    public abstract List<Address> findByUserId(int user_id);
}
