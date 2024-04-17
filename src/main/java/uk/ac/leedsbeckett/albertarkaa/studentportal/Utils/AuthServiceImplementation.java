package uk.ac.leedsbeckett.albertarkaa.studentportal.Utils;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.UserModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Repository.UserRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Service.AuthenticationService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Override
    public Optional<UserModel> getUserByToken(String token) {
        logger.error("An error occurred", token);
        String username = jwtService.extractUsername(token.substring(7)); //remove Bearer from token
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean isAuthorized(UserModel user, int id) {
        return user.getId() == id;
    }
}