package net.vogeez.authorization.service.listener.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Component
@Slf4j
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        // Get the IP address of the client
        String ip = event.getAuthentication().getDetails().toString();
        // Get the Username of the client
        String username = event.getAuthentication().getName();

        log.error("Authentication failed for user {} from IP {}", username, ip);
    }
}
