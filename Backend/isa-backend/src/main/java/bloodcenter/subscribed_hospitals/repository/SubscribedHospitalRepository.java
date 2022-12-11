package bloodcenter.subscribed_hospitals.repository;

import bloodcenter.branch_center.BranchCenter;
import bloodcenter.subscribed_hospitals.model.SubscribedHospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscribedHospitalRepository extends JpaRepository<SubscribedHospital, Long> {

    Optional<SubscribedHospital> findSubscribedHospitalByEmail(String email);
}
