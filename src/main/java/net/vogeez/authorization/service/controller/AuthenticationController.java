package net.vogeez.authorization.service.controller;

import lombok.RequiredArgsConstructor;
import net.vogeez.authorization.service.entity.User;
import net.vogeez.authorization.service.model.SignUpRequest;
import net.vogeez.authorization.service.service.SignUpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final SignUpService signUpService;

    @GetMapping("/")
    public String authenticationPage(@RequestParam(value = "signUp", required = false) String signUp,
                                     Model model) {
        //ToDo Check if user is already logged in if yes redirect to profile page
        if (signUp != null) {
            model.addAttribute("signUpRequest", new SignUpRequest());
        }

        return "authentication";
    }

    @PostMapping("/signUp")
    public String signUpRequest(@ModelAttribute("signUpRequest") @Valid SignUpRequest signUpRequest) {
        Optional<User> user = signUpService.signUpUser(signUpRequest);

        // ToDo add some more information to the user about the sign up process

        if (user.isPresent()) {
            return "redirect:/?message=signUpSuccess";
        }

        return "redirect:/?signUp=failed";
    }
}
