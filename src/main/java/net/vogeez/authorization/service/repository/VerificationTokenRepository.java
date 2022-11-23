package net.vogeez.authorization.service.repository;

import net.vogeez.authorization.service.entity.User;
import net.vogeez.authorization.service.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : Niklas Tat
 * @since : 0.5
 */
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);
    Optional<VerificationToken> findByUser(User user);

    void deleteByUser(User user);
}
