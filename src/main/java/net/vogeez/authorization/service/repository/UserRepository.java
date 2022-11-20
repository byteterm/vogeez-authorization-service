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
 * This Repository is used to access the {@link User} table in the database.
 * It extends the {@link JpaRepository} and provides all the methods to access the database.
 * The {@link User} class is the entity class for the {@link User} table.
 * The {@link Long} class is the type of the primary key of the {@link User} table.
 * The {@link UserRepository} class is annotated with {@link Repository} to make it a Spring Bean.
 *
 * @see Repository
 * @see JpaRepository
 * @see User
 * @see Long
 * @see Transactional
 *
 * @author : Niklas Tat
 * @since : 0.1
 */
@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameIgnoreCase(String usernameOrEmail);
    Optional<User> findByEmailIgnoreCase(String usernameOrEmail);

    @Modifying
    @Query("update User as u set u.password = :p where lower(u.username) = lower(:u)")
    void updateUserPassword(@Param("u") String username, @Param("p") String password);

    @Modifying
    @Query("update User as u set u.lastLoginDate = :d where lower(u.username) = lower(:u)")
    void updateUserLastLoginDate(@Param("u") String username, @Param("d") Date loginDate);

    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByUsernameIgnoreCaseOrEmailIgnoreCase(String username, String email);
}
