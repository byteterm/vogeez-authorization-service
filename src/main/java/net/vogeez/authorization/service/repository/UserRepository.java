package net.vogeez.authorization.service.repository;

import net.vogeez.authorization.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String usernameOrEmail);
    Optional<User> findByEmail(String usernameOrEmail);

    @Modifying
    @Query("update User as u set u.password = :p where u.username = :u")
    void updateUserPassword(@Param("u") String username, @Param("p") String password);

    @Modifying
    @Query("update User as u set u.lastLoginDate = :d where u.username = :u")
    void updateUserLastLoginDate(@Param("u") String username, @Param("d") Date loginDate);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsernameOrEmail(String username, String email);
}