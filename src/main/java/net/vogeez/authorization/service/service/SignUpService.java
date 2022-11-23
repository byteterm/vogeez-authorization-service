package net.vogeez.authorization.service.service;

import net.vogeez.authorization.service.entity.User;
import net.vogeez.authorization.service.model.SignUpRequest;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * This interface is used to sign up a new user
 *
 * @author : Niklas Tat
 * @since : 0.1
 */
public interface SignUpService {

    User signUpUser(SignUpRequest signUpRequest, Locale locale) throws MessagingException, UnsupportedEncodingException;
    boolean existsUserByUsernameOrEmail(String username, String email);
}
