package net.vogeez.authorization.service.service.impl;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import net.vogeez.authorization.service.config.data.EmailConfig;
import net.vogeez.authorization.service.exception.UserAlreadyExistsException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import net.vogeez.authorization.service.entity.User;
import net.vogeez.authorization.service.model.SignUpRequest;
import net.vogeez.authorization.service.repository.UserRepository;
import net.vogeez.authorization.service.service.SignUpService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

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
    private final EmailConfig emailConfig;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Override
    public User signUpUser(SignUpRequest signUpRequest) throws MessagingException, UnsupportedEncodingException {
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
                .enabled(false)
                .emailVerified(false)
                .build()));

        User createdUser = user.orElseThrow(() -> new UserAlreadyExistsException("A user with the given username or email already exists"));

        sendVerificationEmail(createdUser);

        return createdUser;
    }

    @Override
    public boolean existsUserByUsernameOrEmail(String username, String email) {
        return userRepository.existsByUsernameIgnoreCaseOrEmailIgnoreCase(username, email);
    }

    private void sendVerificationEmail(User user) throws UnsupportedEncodingException, MessagingException {
        String verificationCode = RandomString.make(64);

        String fomEmailAddress = emailConfig.fromAddress;
        String emailSenderName = emailConfig.senderName;
        String emailSubject = emailConfig.subject;
        String emailContent = emailConfig.content;

        emailContent = emailContent.replace("%USER_NAME%", user.getUsername());
        emailContent = emailContent.replace("%SENDER_NAME%", emailSenderName);
        emailContent = emailContent.replace("%VERIFICATION_CODE%", verificationCode);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fomEmailAddress, emailSenderName);
        helper.setText(user.getEmail());
        helper.setSubject(emailSubject);
        helper.setText(emailContent, true);

        mailSender.send(message);
    }
}
