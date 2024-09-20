package ch.elca.backendninja.repository;

import ch.elca.backendninja.entity.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("jobPositionRepository")
public interface JobPositionRepository extends JpaRepository<JobPosition, Serializable> {
}
