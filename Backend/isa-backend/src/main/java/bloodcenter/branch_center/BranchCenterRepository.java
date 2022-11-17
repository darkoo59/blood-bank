package bloodcenter.branch_center;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchCenterRepository extends JpaRepository<BranchCenter, Long> {
    @Query(value="SELECT b FROM BranchCenter b WHERE b.name LIKE %?1% OR b.address.country LIKE %?1% OR " +
            "b.address.city LIKE %?1% OR b.address.street LIKE %?1% OR b.address.number LIKE %?1%")
    Page<BranchCenter> findFiltered(String name, Pageable paging);
}
