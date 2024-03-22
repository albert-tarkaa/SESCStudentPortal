package uk.ac.leedsbeckett.albertarkaa.studentportal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.UserModel;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByEmail(String email);
}