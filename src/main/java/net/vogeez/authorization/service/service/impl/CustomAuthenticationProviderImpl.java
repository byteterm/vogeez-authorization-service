package net.vogeez.authorization.service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.vogeez.authorization.service.annotation.Password;
import net.vogeez.authorization.service.annotation.Username;
import net.vogeez.authorization.service.service.CustomAuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

/**
 * This Service is used to authenticate the user.
 * It implements the {@link CustomAuthenticationProvider} interface and provides all the methods to authenticate the user.
 * The {@link CustomAuthenticationProviderImpl} class is annotated with {@link Service} to make it a Spring Bean.
 * The {@link PasswordEncoder} class is used to encode the password.
 *
 * @see Service
 * @see CustomAuthenticationProvider
 * @see PasswordEncoder
 *
 * @author : Niklas Tat
 * @since : 0.5
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProviderImpl implements CustomAuthenticationProvider {

    private final ClientServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        @Valid
        @Username
        String username = authentication.getName();
        @Valid
        @Password
        String password = authentication.getCredentials().toString();

        return checkPassword(userDetailsService.loadUserByUsername(username), password);
    }

    private Authentication checkPassword(UserDetails userDetails, String rawPassword) {
        if (passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        }
        throw new BadCredentialsException("Password is wrong for user: " + userDetails.getUsername());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
