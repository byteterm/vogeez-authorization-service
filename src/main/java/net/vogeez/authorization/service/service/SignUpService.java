package net.vogeez.authorization.service.service;

import net.vogeez.authorization.service.entity.User;
import net.vogeez.authorization.service.model.SignUpRequest;

/**
 * This interface is used to sign up a new user
 *
 * @author : Niklas Tat
 * @since : 0.1
 */
public interface SignUpService {

    User signUpUser(SignUpRequest signUpRequest);
    boolean existsUserByUsernameOrEmail(String username, String email);
}
