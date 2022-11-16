package net.vogeez.authorization.service.config.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import net.vogeez.authorization.service.util.RSAKeyUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.time.Duration;

/**
 * This Configuration class is responsible for the configuration of the OAuth2 Authorization Server.
 * It is used to configure the TokenSettings and the JwtDecoder. The TokenSettings are used to
 * configure the validity of the access and refresh tokens. The JwtDecoder is used to verify the
 * signature of the access tokens.
 *
 * @see JwtDecoder
 * @see JWKSource
 * @see TokenSettings
 *
 * @author : Niklas Tat
 * @since : 0.1
 */
@Configuration
@RequiredArgsConstructor
public class OAuth2KeyConfig {

    /**
     * With this variable you set the Duration how long the access token is valid.
     */
    public static final Duration ACCESS_TOKEN_TIME_TO_LIVE = Duration.ofMinutes(30L);

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = RSAKeyUtil.getRSAKey();
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
