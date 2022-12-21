package bloodcenter.person.repository;

import bloodcenter.person.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value="SELECT u FROM user_ u WHERE LOWER(CONCAT(u.firstname, ' ', u.lastname)) LIKE %?1% OR LOWER(CONCAT(u.lastname, ' ', u.firstname)) LIKE %?1%")
    List<User> searchUsers(String searchInput);

    @Transactional
    @Modifying
    @Query("UPDATE user_ u " + "SET u.enabled = TRUE WHERE u.email = ?1")
    int enableAppUser(String email);
}
