package uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.auth;

import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Service.AuthenticationService;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Utils.ControllerResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ControllerResponse<AuthenticationResponse>> register(@RequestBody RegisterRequest registerRequest) {
       return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<ControllerResponse<AuthenticationResponse>> register(@RequestBody AuthenticationRequest authenticationRequest) throws InvalidCredentialsException {
       return ResponseEntity.ok(authenticationService.login(authenticationRequest));

    }

    @PostMapping("/logout")
    public ResponseEntity<ControllerResponse<AuthenticationResponse>> register(@RequestBody LogoutRequest logoutRequest) {
    return ResponseEntity.ok(authenticationService.logout(logoutRequest));
    }

}
