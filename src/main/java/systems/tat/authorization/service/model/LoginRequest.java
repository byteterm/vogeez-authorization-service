package systems.tat.authorization.service.model;

import systems.tat.authorization.service.annotation.Password;
import systems.tat.authorization.service.annotation.Username;

import javax.validation.constraints.NotBlank;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
public record LoginRequest(
        @NotBlank(message = "Please choose a username")
        @Username
        String username,
        @NotBlank(message = "Please choose a password")
        @Password
        String password
) {
}
