package bloodcenter.person.repository;

import bloodcenter.person.model.BCAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BCAdminRepository extends JpaRepository<BCAdmin, Long> {
    BCAdmin findByEmail(String email);
}
