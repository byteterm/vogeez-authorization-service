package net.vogeez.authorization.service.service.impl;

import lombok.RequiredArgsConstructor;
import net.vogeez.authorization.service.exception.UserAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import net.vogeez.authorization.service.entity.User;
import net.vogeez.authorization.service.model.SignUpRequest;
import net.vogeez.authorization.service.repository.UserRepository;
import net.vogeez.authorization.service.service.SignUpService;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * This Service is used to sign up a new user.
 * It implements the {@link SignUpService} interface and provides all the methods to sign up a new user.
 * The {@link User} class is the entity class for the {@link User} table.
 * The {@link UserRepository} class is used to access the {@link User} table in the database.
 * The {@link PasswordEncoder} class is used to encode the password of the new user.
 * The {@link SignUpServiceImpl} class is annotated with {@link Service} to make it a Spring Bean.
 *
 * @see Service
 * @see SignUpService
 * @see User
 * @see UserRepository
 * @see PasswordEncoder
 *
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

        return user.orElseThrow(() -> new UserAlreadyExistsException("A user with the given username or email already exists"));
    }

    @Override
    public boolean existsUserByUsernameOrEmail(String username, String email) {
        return userRepository.existsByUsernameOrEmail(username, email);
    }
}
