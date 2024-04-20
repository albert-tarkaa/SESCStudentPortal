package uk.ac.leedsbeckett.albertarkaa.studentportal.Utils.Authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.UserModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Utils.ControllerResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationUtility {

    private final AuthServiceImplementation authServiceImplementation;

    public <T> ControllerResponse<T> authorizeUser(String token, int id) {

        Optional<UserModel> userOptional = authServiceImplementation.getUserByToken(token);
        if (userOptional.isEmpty()) {
            return new ControllerResponse<>(false, "User not found", null);
        }

        UserModel user = userOptional.get();
        if (!authServiceImplementation.isAuthorized(user, id)) {
            return new ControllerResponse<>(false, "Unauthorized", null);
        }

        return new ControllerResponse<>(true, null, null); // User is authorized
    }
}