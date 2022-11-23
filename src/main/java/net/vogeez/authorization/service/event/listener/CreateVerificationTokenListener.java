package net.vogeez.authorization.service.event.listener;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import net.vogeez.authorization.service.config.data.EmailConfig;
import net.vogeez.authorization.service.entity.User;
import net.vogeez.authorization.service.entity.VerificationToken;
import net.vogeez.authorization.service.event.CreateVerificationTokenEvent;
import net.vogeez.authorization.service.repository.VerificationTokenRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Locale;

/**
 * @author : Niklas Tat
 * @since : 0.5
 */
@Component
@Transactional
@RequiredArgsConstructor
public class CreateVerificationTokenListener implements ApplicationListener<CreateVerificationTokenEvent> {

    private static final int EXPIRY_TIME_IN_MINUTES = 30;
    private static final int TOKEN_LENGTH = 64;
    private final EmailConfig emailConfig;
    private final MailSender mailSender;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public void onApplicationEvent(CreateVerificationTokenEvent event) {
        User user = event.getUser();

        // Check if the user already has a verification token.
        if (verificationTokenRepository.findByUser(user).isPresent()) {
            verificationTokenRepository.deleteByUser(user);
        }

        VerificationToken verificationToken = createVerificationToken(user);
        sendVerificationToken(user, verificationToken);
    }

    private void sendVerificationToken(User user, VerificationToken verificationToken) {
        String recipientAddress = user.getEmail();
        String subject = emailConfig.subject;
        String content = emailConfig.content
                .replace("%USER_NAME%", user.getUsername())
                .replace("%SENDER_NAME%", emailConfig.senderName)
                .replace("%VERIFICATION_CODE%", verificationToken.getToken());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(emailConfig.fromAddress);
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(content);

        mailSender.send(email);
    }

    private VerificationToken createVerificationToken(User user) {
        String token = RandomString.make(TOKEN_LENGTH);

        if (verificationTokenRepository.findByToken(token).isPresent()) {
            return createVerificationToken(user);
        }

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(verificationToken.calculateExpiryDate(EXPIRY_TIME_IN_MINUTES));

        return verificationTokenRepository.save(verificationToken);
    }
}
