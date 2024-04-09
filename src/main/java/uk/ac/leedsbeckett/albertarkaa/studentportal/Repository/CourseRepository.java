package uk.ac.leedsbeckett.albertarkaa.studentportal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.CourseModel;

public interface CourseRepository extends JpaRepository<CourseModel, Integer>{

    CourseModel findByCourseCode(String courseCode);
}
