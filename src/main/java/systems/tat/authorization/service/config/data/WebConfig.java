package systems.tat.authorization.service.config.data;

import org.springframework.context.annotation.Configuration;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Configuration
public class WebConfig {
    public static final String LOGIN_URL = "/signIn";
    public static final String LOGOUT_URL = "/signOut";
    public static final String LOGIN_FAILURE_URL = LOGIN_URL + "?error";
    public static final String DEFAULT_SUCCESS_URL = "/account/profile";
    public static final String USERNAME_PARAMETER = "username";
    public static final String PASSWORD_PARAMETER = "password";
    public static final String[] AUTH_WHITELIST = {
            // Authentication endpoints
            "/",
            LOGOUT_URL,
            LOGIN_URL,
            "/signUp",
    };
}
