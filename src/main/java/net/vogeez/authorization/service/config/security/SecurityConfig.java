package net.vogeez.authorization.service.config.security;

import lombok.RequiredArgsConstructor;
import net.vogeez.authorization.service.service.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import net.vogeez.authorization.service.config.data.WebConfig;

import javax.servlet.http.HttpServletResponse;

/**
 * This is the main Security Configuration class. Here we configure the SecurityFilterChain
 * for the WebSecurity and the AuthenticationManagerBuilder to configure the AuthenticationProvider.
 *
 * @see SecurityFilterChain
 * @see AuthenticationManagerBuilder
 * @see CustomAuthenticationProvider
 * @see HttpSecurity
 * @see AuthenticationEntryPoint
 * @see AccessDeniedHandler
 * @see SessionCreationPolicy
 * @see HttpMethod
 * @see AntPathRequestMatcher
 * @see WebConfig
 *
 *
 * @author : Niklas Tat
 * @since : 0.1
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String ERROR_FORBIDDEN_MESSAGE = "Error: Forbidden";
    public static final String ERROR_UNAUTHORIZED_MESSAGE = "Error: Unauthorized";

    private final CustomAuthenticationProvider customAuthenticationProvider;

    /**
     * This method configures the the SecurityFilterChain for the WebSecurity.
     * Here we configure the AuthenticationEntryPoint, the AccessDeniedHandler,
     * SessionCreationPolicy, how a request should be handled and the login and logout
     * endpoints and parameters.
     *
     * @see SecurityFilterChain
     * @see HttpSecurity
     * @see SessionCreationPolicy
     * @see WebConfig
     *
     * @param http The HttpSecurity to configure the SecurityFilterChain
     * @return The configured SecurityFilterChain
     * @throws Exception If the SecurityFilterChain could not be configured
     */
    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                // Session management
        .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Requests
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers(WebConfig.ENDPOINTS_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                // Login
                .csrf().disable()
                .formLogin(formLogin -> formLogin
                        .loginPage(WebConfig.AUTHENTICATION_URL)
                        .loginProcessingUrl(WebConfig.LOGIN_URL)
                        .failureUrl(WebConfig.LOGIN_FAILURE_URL)
                        .usernameParameter(WebConfig.USERNAME_PARAMETER)
                        .passwordParameter(WebConfig.PASSWORD_PARAMETER)
                        .defaultSuccessUrl(WebConfig.DEFAULT_SUCCESS_URL)
                )
                // Logout
                .logout()
                .logoutUrl(WebConfig.LOGOUT_URL)
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher(WebConfig.LOGOUT_URL, HttpMethod.GET.name()))
                .logoutSuccessUrl(WebConfig.AUTHENTICATION_URL);
        return http.build();
    }

    @Autowired
    public void bindAuthenticationManager(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> response.sendRedirect(WebConfig.AUTHENTICATION_URL);
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> response.sendError(HttpServletResponse.SC_FORBIDDEN, ERROR_FORBIDDEN_MESSAGE);
    }
}
