package ch.elca.backendninja.repository;

import ch.elca.backendninja.entity.User;
import ch.elca.backendninja.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Serializable> {

    public abstract User findByUsername(String username);
}


