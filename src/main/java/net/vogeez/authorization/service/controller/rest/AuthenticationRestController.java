package net.vogeez.authorization.service.controller.rest;

import lombok.RequiredArgsConstructor;
import net.vogeez.authorization.service.entity.User;
import net.vogeez.authorization.service.model.SignUpRequest;
import net.vogeez.authorization.service.service.SignUpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author : Niklas Tat
 * @since : 0.5
 */
@RestController
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final SignUpService signUpService;

    @PostMapping("/signup")
    @CrossOrigin(origins = "*")
    public ResponseEntity<User> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(signUpService.signUpUser(signUpRequest));
    }
}
