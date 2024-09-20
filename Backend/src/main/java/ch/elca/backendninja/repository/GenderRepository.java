package ch.elca.backendninja.repository;

import ch.elca.backendninja.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("genderRepository")
public interface GenderRepository extends JpaRepository<Gender, Serializable> {
}
