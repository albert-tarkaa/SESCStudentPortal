package uk.ac.leedsbeckett.albertarkaa.studentportal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
}