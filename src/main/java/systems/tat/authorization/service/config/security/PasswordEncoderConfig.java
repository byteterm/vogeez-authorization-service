package systems.tat.authorization.service.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Configuration
public class PasswordEncoderConfig {

    @Value("${tat.authorization-service.password-encoder.secret:change}")
    private String secret;
    @Value("${tat.authorization-service.password-encoder.iterations:18500}")
    private int iterations;
    @Value("${tat.authorization-service.password-encoder.key-length:512}")
    private int hashWidth;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder(secret, iterations, hashWidth);
    }
}
