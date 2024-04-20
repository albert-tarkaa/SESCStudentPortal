package uk.ac.leedsbeckett.albertarkaa.studentportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.UserModel;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByEmail(String email);
}