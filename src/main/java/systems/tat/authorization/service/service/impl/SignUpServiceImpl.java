package systems.tat.authorization.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import systems.tat.authorization.service.entity.Role;
import systems.tat.authorization.service.entity.User;
import systems.tat.authorization.service.model.SignUpRequest;
import systems.tat.authorization.service.repository.UserRepository;
import systems.tat.authorization.service.service.SignUpService;

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
    public Optional<User> signUpUser(SignUpRequest signUpRequest) {
        //ToDo set role

        return userRepository.existsByUsernameOrEmail(signUpRequest.getUsername(), signUpRequest.getEmail()) ?
                Optional.empty() :
                Optional.of(userRepository.save(User.builder()
                        .username(signUpRequest.getUsername())
                        .email(signUpRequest.getEmail())
                        .password(passwordEncoder.encode(signUpRequest.getPassword()))
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .enabled(true)
                        .emailVerified(false)
                        .build()));
    }

    @Override
    public boolean existsUserByUsernameOrEmail(String username, String email) {
        return userRepository.existsByUsernameOrEmail(username, email);
    }
}
