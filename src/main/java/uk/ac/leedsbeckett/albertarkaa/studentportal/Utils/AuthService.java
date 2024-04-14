package uk.ac.leedsbeckett.albertarkaa.studentportal.Utils;

import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.UserModel;

import java.util.Optional;

public interface AuthService {
    Optional<UserModel> getUserByToken(String token);
    boolean isAuthorized(UserModel user, int id);
}