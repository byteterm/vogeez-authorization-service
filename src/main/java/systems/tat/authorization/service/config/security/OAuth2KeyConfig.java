package systems.tat.authorization.service.config.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import systems.tat.authorization.service.config.data.OAuthConfig;
import systems.tat.authorization.service.util.RSAKeyUtil;

import java.time.Duration;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Configuration
@RequiredArgsConstructor
public class OAuth2KeyConfig {

    public static final Duration ACCESS_TOKEN_TIME_TO_LIVE = Duration.ofMinutes(30L);
    private final RSAKeyUtil rsaKeyUtil;

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = rsaKeyUtil.getRSAKey();
        JWKSet jwkSet = new JWKSet(rsaKey);

        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
                .accessTokenTimeToLive(ACCESS_TOKEN_TIME_TO_LIVE)
                .build();
    }
}
