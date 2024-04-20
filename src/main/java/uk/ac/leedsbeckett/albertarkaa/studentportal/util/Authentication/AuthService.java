package uk.ac.leedsbeckett.albertarkaa.studentportal.util.Authentication;

import uk.ac.leedsbeckett.albertarkaa.studentportal.model.UserModel;

import java.util.Optional;

public interface AuthService {
    Optional<UserModel> getUserByToken(String token);
    boolean isAuthorized(UserModel user, int id);
}