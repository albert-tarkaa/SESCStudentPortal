package uk.ac.leedsbeckett.albertarkaa.studentportal.service;

import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.ac.leedsbeckett.albertarkaa.studentportal.dto.request.auth.AuthenticationRequest;
import uk.ac.leedsbeckett.albertarkaa.studentportal.dto.request.auth.MicroservicesStudentIDRequest;
import uk.ac.leedsbeckett.albertarkaa.studentportal.dto.request.auth.RegisterRequest;
import uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.auth.AuthenticationResponse;
import uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.auth.FinanceResponse;
import uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.auth.LibraryResponse;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.StudentModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.UserModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.repository.StudentRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.repository.UserRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.ControllerResponse;
import uk.ac.leedsbeckett.albertarkaa.studentportal.util.Authentication.JwtService;
import uk.ac.leedsbeckett.albertarkaa.studentportal.util.Authentication.Role;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    @Value("${financeUrl}")
    private String financeURL;

    @Value("${libraryUrl}")
    private String libraryURL;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public ControllerResponse<Object> register(RegisterRequest registerRequest) {
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
            Optional <StudentModel> student = studentRepository.findByEmail(user.getEmail());

            // Register student in Finance and Library microservices
            if (student.isEmpty()) return new ControllerResponse<>(false, "Student not found", null);

            String studentID = student.get().getStudentID();
            MicroservicesStudentIDRequest microservicesStudentIDRequest = MicroservicesStudentIDRequest.builder()
                    .StudentId(studentID)
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MicroservicesStudentIDRequest> AccountRequest =
                    new HttpEntity<>(microservicesStudentIDRequest,
                    headers);


            restTemplate.postForObject(financeURL + "/accounts/", AccountRequest, FinanceResponse.class);
            restTemplate.postForObject(libraryURL + "/api/register", AccountRequest, LibraryResponse.class);

            return new ControllerResponse<>(true, null,"User registered successfully");
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

          Optional <StudentModel> student = studentRepository.findByEmail(user.get().getEmail());
            if (student.isEmpty()) {
                return new ControllerResponse<>(false, "Student not found", null);
            }

            var jwtToken = jwtService.generateToken(user.get());
            return new ControllerResponse<>(true, null, AuthenticationResponse.builder()
                    .authToken(jwtToken)
                    .id(user.get().getId())
                    .username(user.get().getUsername())
                    .studentID(student.get().getStudentID())
                    .build());
        } catch (Exception e) {
            logger.error("An error occurred", e);
            return new ControllerResponse<>(false, "An unexpected error occurred while processing your request. Please try again later.", null);
        }

    }
}
