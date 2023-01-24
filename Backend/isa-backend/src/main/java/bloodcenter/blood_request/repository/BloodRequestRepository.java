package bloodcenter.blood_request.repository;

import bloodcenter.blood_request.model.BloodRequest;
import bloodcenter.subscribed_hospitals.model.SubscribedHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {
}
