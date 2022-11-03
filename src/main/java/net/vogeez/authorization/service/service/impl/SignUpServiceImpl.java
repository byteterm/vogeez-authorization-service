package net.vogeez.authorization.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import net.vogeez.authorization.service.entity.User;
import net.vogeez.authorization.service.model.SignUpRequest;
import net.vogeez.authorization.service.repository.UserRepository;
import net.vogeez.authorization.service.service.SignUpService;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author : Niklas Tat
 * @since : 0.5
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signUpUser(SignUpRequest signUpRequest) {
        //ToDo set role

        if (!signUpRequest.getPassword().equals(signUpRequest.getPasswordRepeat()))
            throw new IllegalArgumentException("Passwords do not match");


        Optional<User> user = Optional.of(userRepository.save(User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .emailVerified(false)
                .build()));

        return user.orElseThrow(() -> new RuntimeException("User could not be created"));
    }

    @Override
    public boolean existsUserByUsernameOrEmail(String username, String email) {
        return userRepository.existsByUsernameOrEmail(username, email);
    }
}
