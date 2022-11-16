package net.vogeez.authorization.service.listener.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * This Component listens for {@link AuthenticationFailureBadCredentialsEvent}s.
 * It logs how many times a user has tried to login with wrong credentials.
 * If the user has tried to login 5 times with wrong credentials, the user will be locked.
 * The user will be unlocked after after 5 minutes.
 *
 * @see Component
 * @see ApplicationListener
 * @see AuthenticationFailureBadCredentialsEvent
 *
 * @author : Niklas Tat
 * @since : 0.1
 */
@Component
@Slf4j
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        //ToDo log how many times a user has tried to login with wrong credentials and lock the user after 5 times.

        String ip = event.getAuthentication().getDetails().toString();
        String username = event.getAuthentication().getName();

        log.error("Authentication failed for user {} from IP {}", username, ip);
    }
}
