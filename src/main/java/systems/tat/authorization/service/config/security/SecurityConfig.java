package systems.tat.authorization.service.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import systems.tat.authorization.service.config.data.WebConfig;
import systems.tat.authorization.service.service.CustomAuthenticationProvider;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] COOKIES_TO_DELETE = {"JSESSIONID"};
    private static final String ERROR_FORBIDDEN_MESSAGE = "Error: Forbidden";

    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                // Session management
        .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                // Requests
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers(WebConfig.AUTH_WHITELIST).permitAll()
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
                .deleteCookies(COOKIES_TO_DELETE)
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
