package net.vogeez.authorization.service.model;

import lombok.Getter;
import lombok.Setter;
import net.vogeez.authorization.service.annotation.AcceptTerms;
import net.vogeez.authorization.service.annotation.Password;
import net.vogeez.authorization.service.annotation.Username;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * This Model class represents a SignUpRequest. It contains all the information
 * that we need to create a new user. The information is validated by the
 * annotations.
 *
 * @see NotBlank
 * @see NotNull
 * @see Username
 * @see Email
 * @see Password
 * @see AcceptTerms
 *
 * @author : Niklas Tat
 * @since : 0.1
 */
@Getter
@Setter
public class SignUpRequest {

        @NotBlank
        @Username
        private String username;

        @Email(message = "Email is not valid")
        @NotBlank
        private String email;

        @NotBlank
        @Password
        private String password;

        @NotBlank
        @Password
        private String passwordRepeat;

        @NotNull
        @AcceptTerms
        private boolean acceptTerms;
}
