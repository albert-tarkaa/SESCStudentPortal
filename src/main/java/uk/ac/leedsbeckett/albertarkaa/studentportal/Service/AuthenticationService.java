package uk.ac.leedsbeckett.albertarkaa.studentportal.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.auth.AuthenticationRequest;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.auth.AuthenticationResponse;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.auth.RegisterRequest;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.UserModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Repository.UserRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Utils.JwtService;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Utils.Role;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = UserModel.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .createdAt(java.time.LocalDateTime.now())
                .lastLogin(java.time.LocalDateTime.now())
                .role(Role.STUDENT)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        var refreshExpireDate = LocalDateTime.now().plusSeconds(jwtService.extractExpiration(refreshToken).getTime());
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .refreshToken(jwtToken)
                .expiresAt(refreshExpireDate.toString())
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword()));
        var user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
       user.setLastLogin(java.time.LocalDateTime.now());
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .build();
    }

}
