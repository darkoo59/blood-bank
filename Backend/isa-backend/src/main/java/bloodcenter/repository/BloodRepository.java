package bloodcenter.repository;

import bloodcenter.enums.BloodType;
import bloodcenter.model.Blood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BloodRepository extends JpaRepository<Blood, Long> {

    Optional<Blood> findBloodByType(BloodType bloodType);
}
