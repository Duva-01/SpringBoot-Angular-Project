package ch.elca.backendninja.repository;

import ch.elca.backendninja.entity.Address;
import ch.elca.backendninja.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("userImageRepository")
public interface UserImageRepository extends JpaRepository<UserImage, Serializable> {

    public abstract List<UserImage> findByUserId(int userId);
}
