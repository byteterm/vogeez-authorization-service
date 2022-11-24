package net.vogeez.authorization.service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.vogeez.authorization.service.annotation.Username;
import net.vogeez.authorization.service.endpoint.ViewNames;
import net.vogeez.authorization.service.entity.VerificationToken;
import net.vogeez.authorization.service.event.CreateVerificationTokenEvent;
import net.vogeez.authorization.service.repository.VerificationTokenRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import net.vogeez.authorization.service.entity.User;
import net.vogeez.authorization.service.repository.UserRepository;
import net.vogeez.authorization.service.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

/**
 * This Service is used to handle all the user related operations.
 * It implements the {@link UserService} interface and provides all the methods to access the database.
 * The {@link User} class is the entity class for the {@link User} table.
 * The {@link UserRepository} class is used to access the {@link User} table in the database.
 * The {@link UserServiceImpl} class is annotated with {@link Service} to make it a Spring Bean.
 *
 * @see Service
 * @see UserService
 * @see User
 * @see UserRepository
 *
 * @author : Niklas Tat
 * @since : 0.5
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public UserDetails loadUserByUsername(@Valid @Username String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> user;
        if (usernameOrEmail.contains("@")) {
            user = userRepository.findByEmailIgnoreCase(usernameOrEmail);
        } else {
            user = userRepository.findByUsernameIgnoreCase(usernameOrEmail);
        }

        User foundUser = user.orElseThrow(() -> new UsernameNotFoundException("Username or Email {} not found" + usernameOrEmail));

        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
                foundUser.getUsername(),
                foundUser.getPassword(),
                foundUser.isEnabled(),
                foundUser.isAccountNonExpired(),
                foundUser.isCredentialsNonExpired(),
                foundUser.isAccountNonLocked(),
                foundUser.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()
        );

        new AccountStatusUserDetailsChecker().check(userDetails);

        return userDetails;
    }

    @Override
    public boolean verifyEmailToken(String token, String email) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);

        if (verificationToken.isEmpty()
                || !verificationToken.get().getUser().getEmail().equals(email))
            return false;

        User user = verificationToken.get().getUser();
        user.setEnabled(true);
        user.setEmailVerified(true);

        userRepository.save(user);
        verificationTokenRepository.deleteByUser(user);

        return true;
    }

    @Override
    public void resendVerificationToken(String email) {
        User user = userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException("Email {} not found" + email));

        if (!user.isEnabled()
                && !user.isEmailVerified()) {
            eventPublisher.publishEvent(new CreateVerificationTokenEvent(user));
        }
    }
}
