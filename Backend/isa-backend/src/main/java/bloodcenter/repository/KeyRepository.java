package bloodcenter.repository;

import bloodcenter.model.Blood;
import bloodcenter.model.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends JpaRepository<Key, Long> {

}