package systems.tat.authorization.service.config.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Configuration
public class OAuthConfig {

    @Value("${tat.authorization-service.oauth2.issuer:https://accounts.tat.systems}")
    public static String issuer;
    @Value("${tat.authorization-service.oauth2.authorization-endpoint:/oauth/authorize}")
    public static String authorizationEndpoint;
    @Value("${tat.authorization-service.oauth2.token-endpoint:/oauth/token}")
    public static String tokenEndpoint;
    @Value("${tat.authorization-service.oauth2.jwk-set-endpoint:/oauth/jwks}")
    public static String jwkSetEndpoint;
    @Value("${tat.authorization-service.oauth2.token-revocation-endpoint:/oauth/revoke}")
    public static String tokenRevocationEndpoint;
    @Value("${tat.authorization-service.oauth2.token-introspection-endpoint:/oauth/introspect}")
    public static String tokenIntrospectionEndpoint;
    @Value("${tat.authorization-service.oauth2.oidc-client-registration-endpoint:/connect/register}")
    public static String oidcClientRegistrationEndpoint;
    @Value("${tat.authorization-service.oauth2.oidc-user-info-endpoint:/userinfo}")
    public static String oidcUserInfoEndpoint;
    @Value("${tat.authorization-service.oauth2.rsa.private-key}")
    public static String rsaPrivateKey;
    @Value("${tat.authorization-service.oauth2.rsa.public-key}")
    public static String rsaPublicKey;

    @Value("${tat.authorization-service.oauth2.access-token-validity-minutes:30L}")
    public static Long accessTokenValidityMinutes;
}
