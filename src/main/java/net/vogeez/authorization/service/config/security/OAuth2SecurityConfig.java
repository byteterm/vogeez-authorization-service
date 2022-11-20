package net.vogeez.authorization.service.config.security;

import lombok.RequiredArgsConstructor;
import net.vogeez.authorization.service.endpoint.ViewNames;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;
import net.vogeez.authorization.service.config.data.OAuthConfig;
import net.vogeez.authorization.service.config.data.WebConfig;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * With this Configuration class, we can configure the OAuth2 Authorization Server.
 * Here we configure the SecurityFilterChain for the OAuth2 Authorization Server,
 * the RegisteredClientRepository to store / get the registered clients and with the
 * ProviderSettings we can configure the issuer of the tokens and the endpoints.
 *
 * @see SecurityFilterChain
 * @see OAuth2AuthorizationServerConfiguration
 * @see RegisteredClientRepository
 * @see JdbcOperations
 * @see ProviderSettings
 *
 * @author : Niklas Tat
 * @since : 0.1
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class OAuth2SecurityConfig {

    private final OAuthConfig oAuthConfig;

    @Bean
    @Order(1)
    public SecurityFilterChain oauthSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(ViewNames.AUTHENTICATION.getUrl()))
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
