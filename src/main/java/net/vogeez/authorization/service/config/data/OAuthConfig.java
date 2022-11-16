package net.vogeez.authorization.service.config.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * This Configuration class contains all the configurable values for the oAuth2
 * server. The values are read from the application.properties file or the environment
 * variables. In the startup process, the values will be injected into this class.
 * Above every variable you see the @Value annotation. This annotation is used to
 * read the value from the application.properties file or the environment variables.
 * The ":" is used to set a default value if the value is not set in the application.properties
 * or over the environment variables.
 * <p>
 * The values can be accessed by any Component in this application. Just create a final
 * field of this class and annotate it with @Autowired or just create a constructor in the Component
 * class that requires this class as an argument.
 * <p>
 * If you use the application.properties file to change the values just use the following syntax:
 *  Example for changing the issuer:
 *      vogeez.authorization-service.oauth2.issuer=https://vogeez.net
 * <p>
 * If you want to use the environment variables as startup arguments, just use the following syntax:
 *  Example for changing the issuer:
 *      java -jar authorization-service.jar --vogeez.authorization-service.oauth2.issuer=https://vogeez.net
 *
 * @see Value
 *
 * @author : Niklas Tat
 * @since : 0.1
 */
@Configuration
public class OAuthConfig {

    @Value("${vogeez.authorization-service.oauth2.issuer:https://accounts.vogeez.com}")
    public String issuer;
    @Value("${vogeez.authorization-service.oauth2.authorization-endpoint:/oauth2/authorize}")
    public String authorizationEndpoint;
    @Value("${vogeez.authorization-service.oauth2.token-endpoint:/oauth2/token}")
    public String tokenEndpoint;
    @Value("${vogeez.authorization-service.oauth2.jwk-set-endpoint:/oauth2/jwks}")
    public String jwkSetEndpoint;
    @Value("${vogeez.authorization-service.oauth2.token-revocation-endpoint:/oauth2/revoke}")
    public String tokenRevocationEndpoint;
    @Value("${vogeez.authorization-service.oauth2.token-introspection-endpoint:/oauth2/introspect}")
    public String tokenIntrospectionEndpoint;
    @Value("${vogeez.authorization-service.oauth2.oidc-client-registration-endpoint:/connect/register}")
    public String oidcClientRegistrationEndpoint;
    @Value("${vogeez.authorization-service.oauth2.oidc-user-info-endpoint:/userinfo}")
    public String oidcUserInfoEndpoint;
    @Value("${vogeez.authorization-service.oauth2.registeredClientId:vogeez}")
    public String registeredClientId;
}
