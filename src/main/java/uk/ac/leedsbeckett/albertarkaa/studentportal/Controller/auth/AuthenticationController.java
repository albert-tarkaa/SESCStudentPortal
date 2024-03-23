package uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest authenticationRequest) {
       return ResponseEntity.ok(authenticationService.login(authenticationRequest));

    }

/*    @PostMapping("/logout")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody LogoutRequest logoutRequest) {

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {

    }*/

}
