package net.vogeez.authorization.service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import net.vogeez.authorization.service.entity.User;
import net.vogeez.authorization.service.repository.UserRepository;
import net.vogeez.authorization.service.service.ClientService;

import java.util.Optional;

/**
 * @author : Niklas Tat
 * @since : 0.5
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> user;
        if (usernameOrEmail.contains("@")) {
            user = userRepository.findByEmail(usernameOrEmail);
        } else {
            user = userRepository.findByUsername(usernameOrEmail);
        }

        user.orElseThrow(() -> new UsernameNotFoundException("Username or Email {} not found" + usernameOrEmail));

        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
                user.get().getUsername(),
                user.get().getPassword(),
                user.get().isEnabled(),
                user.get().isAccountNonExpired(),
                user.get().isCredentialsNonExpired(),
                user.get().isAccountNonLocked(),
                user.get().getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()
        );

        new AccountStatusUserDetailsChecker().check(userDetails);

        return userDetails;
    }
}
