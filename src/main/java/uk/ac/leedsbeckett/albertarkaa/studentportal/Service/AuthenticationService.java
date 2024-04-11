package uk.ac.leedsbeckett.albertarkaa.studentportal.Service;

import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.auth.AuthenticationRequest;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.auth.AuthenticationResponse;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.auth.LogoutRequest;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.auth.RegisterRequest;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.UserModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Repository.StudentRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Repository.UserRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Utils.ControllerResponse;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Utils.JwtService;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Utils.Role;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public ControllerResponse<AuthenticationResponse> register(RegisterRequest registerRequest) {
        try {
            var user = UserModel.builder()
                    .username(registerRequest.getUsername())
                    .email(registerRequest.getEmail())
                    .createdAt(java.time.LocalDateTime.now())
                    .lastLogin(java.time.LocalDateTime.now())
                    .role(Role.STUDENT)
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .build();
            userRepository.save(user);
            // Create student record
            studentRepository.save(studentService.createStudentFromUser(user));

            var jwtToken = jwtService.generateToken(user);
            return new ControllerResponse<>(true, null, AuthenticationResponse.builder()
                    .authToken(jwtToken)
                    .id(user.getId())
                    .build());
        } catch (Exception e) {
            logger.error("An error occurred", e);
            return new ControllerResponse<>(false, "An unexpected error occurred while processing your request. Please try again later.", null);
        }
    }

    public ControllerResponse <AuthenticationResponse> login(AuthenticationRequest authenticationRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            Optional<UserModel> user = userRepository.findByUsername(authenticationRequest.getUsername());

            if (user.isEmpty()) {
                throw new InvalidCredentialsException("Invalid username or password");
            }

            user.get().setLastLogin(LocalDateTime.now());
            userRepository.save(user.get());

            var jwtToken = jwtService.generateToken(user.get());
            return new ControllerResponse<>(true, null, AuthenticationResponse.builder()
                    .authToken(jwtToken)
                    .id(user.get().getId())
                    .build());
        } catch (Exception e) {
            logger.error("An error occurred", e);
            return new ControllerResponse<>(false, "An unexpected error occurred while processing your request. Please try again later.", null);
        }

    }

    public ControllerResponse <AuthenticationResponse> logout(LogoutRequest logoutRequest) {
        try {
            return new ControllerResponse<>(true, null, null);
        } catch (Exception e) {
            return new ControllerResponse<>(false, e.getMessage(), null);
        }
    }
}
