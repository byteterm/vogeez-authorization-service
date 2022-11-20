package net.vogeez.authorization.service.config.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * With this Configuration class, we can configure the PasswordEncoder.
 * Here we configure the PasswordEncoder to use the Pbkdf2PasswordEncoder.
 * The Pbkdf2PasswordEncoder is a password encoder that uses the PBKDF2 hashing function.
 * The Pbkdf2PasswordEncoder is a strong password encoder that is recommended for password storage.
 * As parameters, we can configure the secret of the password encoder, the number of iterations and
 * the hashWidth. You can change the values of the variables in the application.properties file or
 * over the environment variables. In the @Value annotation you can see the syntax for changing the
 * values. The default values are set in the @Value annotation. You can see the default values after
 * the ":".
 * <p>
 * The values can be accessed by any Component in this application. Just create a final field of
 * this class and annotate it with @Autowired or just create a constructor in the Component class
 * that requires this class as an argument.
 * <p>
 * If you use the application.properties file to change the values just use the following syntax:
 *  Example for changing the secret:
 *      vogeez.authorization-service.password-encoder.secret=newSecret
 * <p>
 * If you want to use the environment variables as startup arguments, just use the following syntax:
 *  Example for changing the secret:
 *      java -jar authorization-service.jar --vogeez.authorization-service.password-encoder.secret=newSecret
 *
 * @see PasswordEncoder
 * @see Pbkdf2PasswordEncoder
 * @see Value
 *
 * @author : Niklas Tat
 * @since : 0.1
 */
@Configuration
public class PasswordEncoderConfig {

    @Value("${vogeez.authorization-service.password-encoder.secret:change}")
    private String secret;
    @Value("${vogeez.authorization-service.password-encoder.iterations:18500}")
    private int iterations;
    @Value("${vogeez.authorization-service.password-encoder.key-length:512}")
    private int hashWidth;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder(secret, iterations, hashWidth);
    }
}
