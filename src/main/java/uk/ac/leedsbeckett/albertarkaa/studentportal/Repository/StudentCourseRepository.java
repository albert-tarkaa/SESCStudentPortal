package uk.ac.leedsbeckett.albertarkaa.studentportal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.CourseModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.StudentCourseModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.StudentModel;

public interface StudentCourseRepository extends JpaRepository<StudentCourseModel, Integer> {
    StudentCourseModel findByStudentAndCourse(StudentModel student, CourseModel course);
}
