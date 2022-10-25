package systems.tat.authorization.service.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;
import systems.tat.authorization.service.config.data.OAuthConfig;
import systems.tat.authorization.service.config.data.WebConfig;

import java.util.UUID;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class OAuth2SecurityConfig {

    private final OAuthConfig oAuthConfig;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain oauthSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.formLogin(formLogin -> formLogin
                .loginPage(WebConfig.AUTHENTICATION_URL)
                .loginProcessingUrl(WebConfig.LOGIN_URL)
        );
        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcOperations jdbcOperations) {
        return new JdbcRegisteredClientRepository(jdbcOperations);
    }

    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder()
                .authorizationEndpoint(oAuthConfig.authorizationEndpoint)
                .tokenEndpoint(oAuthConfig.tokenEndpoint)
                .jwkSetEndpoint(oAuthConfig.jwkSetEndpoint)
                .tokenRevocationEndpoint(oAuthConfig.tokenRevocationEndpoint)
                .tokenIntrospectionEndpoint(oAuthConfig.tokenIntrospectionEndpoint)
                .oidcClientRegistrationEndpoint(oAuthConfig.oidcClientRegistrationEndpoint)
                .oidcUserInfoEndpoint(oAuthConfig.oidcUserInfoEndpoint)
                .issuer(oAuthConfig.issuer)
                .build();
    }
}
