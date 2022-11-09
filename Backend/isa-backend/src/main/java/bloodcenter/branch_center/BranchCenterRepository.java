package bloodcenter.branch_center;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchCenterRepository  extends JpaRepository<BranchCenter, Long> {
    @Query(value="SELECT * FROM BRANCH_CENTER WHERE name LIKE CONCAT(:name,'%')",
            nativeQuery = true)
    Page<BranchCenter> findByNameContaining(String name, Pageable paging);
}
