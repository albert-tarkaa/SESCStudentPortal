package uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Service.AuthenticationService;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Utils.ControllerResponse;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ControllerResponse<Object>> register(@RequestBody RegisterRequest registerRequest) {
        var response = authenticationService.register(registerRequest);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ControllerResponse<AuthenticationResponse>> register(@RequestBody AuthenticationRequest authenticationRequest) {
        ControllerResponse<AuthenticationResponse> response = authenticationService.login(authenticationRequest);
        if (response.isSuccess()) return ResponseEntity.ok(response);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);

    }
}
