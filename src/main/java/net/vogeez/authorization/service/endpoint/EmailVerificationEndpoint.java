package net.vogeez.authorization.service.endpoint;

import lombok.RequiredArgsConstructor;
import net.vogeez.authorization.service.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : Niklas Tat
 * @since : 0.5
 */
@Controller("/code")
@RequiredArgsConstructor
public class EmailVerificationEndpoint {

    private final UserService userService;

    @GetMapping("/resend")
    public String resendVerificationToken(@RequestParam(value = "email") String email, Model model) {
        userService.resendVerificationToken(email);
        model.addAttribute("emailVerification.resend", "A new verification email has been sent to your email address.");

        return ViewNames.AUTHENTICATION.getViewName();
    }

    @GetMapping("/verify")
    public String verifyVerificationToken(@RequestParam(value = "token") String token, @RequestParam(value = "email") String email, Model model) {
        if (userService.verifyEmailToken(token, email)) {
            model.addAttribute("emailVerification.success", "Your email has been verified successfully");
        } else {
            model.addAttribute("emailVerification.failed", "Your email could not be verified");
        }

        return ViewNames.AUTHENTICATION.getViewName();
    }
}
