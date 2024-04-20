package uk.ac.leedsbeckett.albertarkaa.studentportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.StudentModel;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentModel, Integer> {
    boolean existsByEmail(String email);

    Optional<StudentModel> findByEmail(String email);

    StudentModel findById(int id);



}
