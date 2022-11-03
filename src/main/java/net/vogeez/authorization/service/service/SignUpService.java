package net.vogeez.authorization.service.service;

import net.vogeez.authorization.service.entity.User;
import net.vogeez.authorization.service.model.SignUpRequest;

import java.util.Optional;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
public interface SignUpService {

    User signUpUser(SignUpRequest signUpRequest);
    boolean existsUserByUsernameOrEmail(String username, String email);
}
