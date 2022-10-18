package systems.tat.authorization.service.listener.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Slf4j
@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        // Get the IP address of the client
        String ip = event.getAuthentication().getDetails().toString();
        // Get the Username of the client
        String username = event.getAuthentication().getName();

        log.info("Authentication successful for user {} from IP {}", username, ip);
    }
}
