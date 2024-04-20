package uk.ac.leedsbeckett.albertarkaa.studentportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.CourseModel;

import java.util.List;

public interface CourseRepository extends JpaRepository<CourseModel, Integer>{

    CourseModel findByCourseCode(String courseCode);
    List<CourseModel> findByCourseNameContainingIgnoreCase (String CourseName);
}
