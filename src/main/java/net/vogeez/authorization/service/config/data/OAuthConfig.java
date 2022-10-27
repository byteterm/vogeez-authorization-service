package net.vogeez.authorization.service.config.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
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
    @Value("${vogeez.authorization-service.oauth2.rsa.private-key:?}")
    public String rsaPrivateKey;
    @Value("${vogeez.authorization-service.oauth2.rsa.public-key:?}")
    public String rsaPublicKey;
}
