package uk.ac.leedsbeckett.albertarkaa.studentportal.util.Authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.UserModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public Optional<UserModel> getUserByToken(String token) {
        String username = jwtService.extractUsername(token.substring(7)); //remove Bearer from token
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean isAuthorized(UserModel user, int id) {
        return user.getId() == id;
    }
}