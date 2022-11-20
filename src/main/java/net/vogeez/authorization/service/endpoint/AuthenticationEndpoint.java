package net.vogeez.authorization.service.endpoint;

import lombok.RequiredArgsConstructor;
import net.vogeez.authorization.service.model.SignUpRequest;
import net.vogeez.authorization.service.service.SignUpService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * This Controller is to handle the Authentication endpoints.
 * Here we have the login and signIn endpoints. The login endpoint
 * is used to login the user and the signIn endpoint is used to
 * sign in a new user. The signIn endpoint is only used for the
 * first sign in of a user. After the first sign in the user
 * can login with the login endpoint.
 *
 * @see Controller
 * @see GetMapping
 * @see PostMapping
 * @see RequiredArgsConstructor
 * @see SignUpService
 *
 * @author : Niklas Tat
 * @since : 0.5
 */
@Controller
@RequiredArgsConstructor
public class AuthenticationEndpoint {

    private final SignUpService signUpService;

    /**
     * This method gets every request to the "/" endpoint
     * and displays the login / sign up page. If the user
     * is already logged in, the user gets redirected to
     * the account dashboard. The SignIn request is handled by
     * the "/signin" endpoint and the SignUp request is handled
     * by the "/signup" endpoint.
     *
     * @return the authentication page
     * @see GetMapping
     * @see RequestParam
     * @see Model
     */
    @GetMapping("/")
    public String displayAuthenticationPage(@RequestParam(value = "signup", required = false) String signUp, Model model, Authentication authentication) {
        // Check if the user is already logged in
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return ViewNames.ACCOUNT_DASHBOARD.getViewName();
        }

        if (signUp != null) {
            model.addAttribute("signIn", true);
        }

        model.addAttribute("signUpRequest", new SignUpRequest());

        return ViewNames.AUTHENTICATION.getViewName();
    }

    /**
     * This method handles the sign-up request from the user ("/signup").
     * If the user is already logged in, the user gets redirected to the
     * account dashboard. If the user is not logged in, the user gets
     * redirected to the login page.
     * The SignUpRequest is validated by the {@link Valid} annotation.
     * The SignUpRequest is passed to the {@link SignUpService} to sign up
     * the user.
     *
     * @see PostMapping
     * @see Valid
     * @see ModelAttribute
     * @see SignUpRequest
     * @see SignUpService
     *
     * @param signUpRequest the sign-up request from the with the needed information to sign up the user
     * @return the authentication page
     */
    @PostMapping("/signup")
    public String signIn(@Valid @ModelAttribute("signUpRequest") SignUpRequest signUpRequest, BindingResult bindingResult, Model model, Authentication authentication) {
        // Check if the user is already logged in
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return ViewNames.ACCOUNT_DASHBOARD.getViewName();
        }

        // Check for validation errors in the sign-up request
        if (bindingResult.hasErrors()) {
            model.addAttribute("signIn", true);
            return ViewNames.AUTHENTICATION.getViewName();
        }

        signUpService.signUpUser(signUpRequest);

        return ViewNames.AUTHENTICATION.getViewName();
    }
}
