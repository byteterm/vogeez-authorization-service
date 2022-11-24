package net.vogeez.authorization.service.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * This is used to handle all the user related operations
 *
 * @author : Niklas Tat
 * @since : 0.5
 */
public interface UserService extends UserDetailsService {

    boolean verifyEmailToken(String token, String email);

    void resendVerificationToken(String email);
}
