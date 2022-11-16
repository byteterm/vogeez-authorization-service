package net.vogeez.authorization.service.endpoint;

import lombok.RequiredArgsConstructor;
import net.vogeez.authorization.service.model.SignUpRequest;
import net.vogeez.authorization.service.service.SignUpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
     * @see GetMapping
     * @see RequestParam
     * @see Model
     *
     * @return the login and signup page
     */
    @GetMapping("/")
    public String displayAuthenticationPage(@RequestParam(value = "signup", required = false) String signUp, Model model) {
        if (signUp != null) {
            model.addAttribute("signUpRequest", new SignUpRequest());
        }

        return "authentication";
    }

}
