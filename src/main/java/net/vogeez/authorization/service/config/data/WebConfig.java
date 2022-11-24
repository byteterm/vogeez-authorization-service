package net.vogeez.authorization.service.config.data;

import net.vogeez.authorization.service.endpoint.ViewNames;

/**
 * This Config class is used to store all endpoints and whitelisted endpoints there
 * are needed for the login process or to get access without a logged user. This class
 * help to be sure that the endpoints are not changed by accident and for a better
 * overview of the endpoints, whitelisted endpoints and the login parameters.
 *
 * @author : Niklas Tat
 * @since : 0.1
 */
public class WebConfig {

    private WebConfig() {
    }

    public static final String LOGIN_PROCESS_URL = "/signin";
    public static final String LOGOUT_URL = "/logout";
    public static final String LOGIN_FAILURE_URL = ViewNames.AUTHENTICATION.getUrl() + "?error";
    public static final String DEFAULT_SUCCESS_URL = ViewNames.ACCOUNT_DASHBOARD.getUrl();
    public static final String USERNAME_PARAMETER = "username";
    public static final String PASSWORD_PARAMETER = "password";
    public static final String[] ENDPOINTS_WHITELIST = {
            // static resources
            "/css/**",
            // Authentication endpoints
            ViewNames.AUTHENTICATION.getUrl(),
            LOGIN_PROCESS_URL,
            "/signup",
            // Email verification endpoints
            ViewNames.EMAIL_VERIFICATION.getUrl(),
            ViewNames.EMAIL_VERIFICATION_RESEND.getUrl(),
    };
}
