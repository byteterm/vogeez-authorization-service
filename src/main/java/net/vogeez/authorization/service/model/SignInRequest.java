package net.vogeez.authorization.service.model;

import lombok.Getter;
import lombok.Setter;
import net.vogeez.authorization.service.annotation.Password;
import net.vogeez.authorization.service.annotation.Username;

import javax.validation.constraints.NotBlank;

/**
 * @author : Niklas Tat
 * @since : 0.5
 */
@Getter
@Setter
public class SignInRequest {

    @NotBlank
    @Username
    private String username;

    @NotBlank
    @Password
    private String password;
}
