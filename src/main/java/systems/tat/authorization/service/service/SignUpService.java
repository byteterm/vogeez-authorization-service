package systems.tat.authorization.service.service;

import systems.tat.authorization.service.entity.User;
import systems.tat.authorization.service.model.SignUpRequest;

import java.util.Optional;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
public interface SignUpService {

    Optional<User> signUpUser(SignUpRequest signUpRequest);
    boolean existsUserByUsernameOrEmail(String username, String email);
}
