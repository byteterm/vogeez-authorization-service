package net.vogeez.authorization.service.config.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Niklas Tat
 * @since : 0.5
 */
@Configuration
public class EmailConfig {

    @Value("${vogeez.authorization-service.email.from-address:verification@vogeez.net}")
    public String fromAddress;
    @Value("${vogeez.authorization-service.email.sender-name:Vogeez}")
    public String senderName;
    @Value("${vogeez.authorization-service.email.subject:Vogeez - Verify your email address}")
    public String subject;
    @Value("${vogeez.authorization-service.email.content:Hello %USER_NAME%,<br>Thank you for registering at %SENDER_NAME%. Please click on the following link to verify your email address:<br><a href=\"http://localhost:8080/code/verify?%VERIFICATION_CODE%\">Verify your email address</a><br><br><br>Best regards,<br><br>%SENDER_NAME%}")
    public String content;
}
