package bloodcenter.user.repository;

import bloodcenter.user.model.BCAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BCAdminRepository extends JpaRepository<BCAdmin, Long> {
}
