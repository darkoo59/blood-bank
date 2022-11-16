package bloodcenter.user.repository;

import bloodcenter.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query(value="SELECT u FROM user_ u WHERE LOWER(u.firstname) LIKE %?1% OR LOWER(u.lastname) LIKE %?1%")
    List<User> searchUsers(String searchInput);
}
