package net.vogeez.authorization.service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.vogeez.authorization.service.annotation.Username;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import net.vogeez.authorization.service.entity.User;
import net.vogeez.authorization.service.repository.UserRepository;
import net.vogeez.authorization.service.service.ClientService;

import javax.validation.Valid;
import java.util.Optional;

/**
 * This Service is used to handle all the user related operations.
 * It implements the {@link ClientService} interface and provides all the methods to access the database.
 * The {@link User} class is the entity class for the {@link User} table.
 * The {@link UserRepository} class is used to access the {@link User} table in the database.
 * The {@link ClientServiceImpl} class is annotated with {@link Service} to make it a Spring Bean.
 *
 * @see Service
 * @see ClientService
 * @see User
 * @see UserRepository
 *
 * @author : Niklas Tat
 * @since : 0.5
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@Valid @Username String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> user;
        if (usernameOrEmail.contains("@")) {
            user = userRepository.findByEmail(usernameOrEmail);
        } else {
            user = userRepository.findByUsername(usernameOrEmail);
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
}
