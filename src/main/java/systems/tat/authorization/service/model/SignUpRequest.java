package systems.tat.authorization.service.model;

import lombok.Getter;
import lombok.Setter;
import systems.tat.authorization.service.annotation.Password;
import systems.tat.authorization.service.annotation.Username;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Getter
@Setter
public class SignUpRequest {

        @NotBlank
        @Username
        private String username;

        @Email
        @NotBlank
        private String email;

        @NotBlank
        @Password
        private String password;

        //@NotBlank
        //@Password
        //private String passwordRepeat;
}
