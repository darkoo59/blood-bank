package bloodcenter.subscribed_hospitals.repository;

import bloodcenter.subscribed_hospitals.model.SubscribedHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribedHospitalRepository extends JpaRepository<SubscribedHospital, Long> {
}
