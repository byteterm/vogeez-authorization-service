package systems.tat.authorization.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import systems.tat.authorization.service.entity.User;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Query("update User as u set u.password = :p where u.username = :u")
    void updateUserPassword(@Param("u") String username, @Param("p") String password);

    @Modifying
    @Query("update User as u set u.lastLoginDate = :l where u.username = :u")
    void updateUserLastLoginDate(@Param("u") String username, @Param("d") Date loginDate);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsernameOrEmail(String username, String email);
}
