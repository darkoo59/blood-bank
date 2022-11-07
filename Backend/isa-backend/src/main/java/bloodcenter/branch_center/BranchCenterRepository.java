package bloodcenter.branch_center;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchCenterRepository  extends JpaRepository<BranchCenter, Long> {

}
