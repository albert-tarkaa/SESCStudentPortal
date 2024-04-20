package uk.ac.leedsbeckett.albertarkaa.studentportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.CourseModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.StudentCourseModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.StudentModel;

public interface StudentCourseRepository extends JpaRepository<StudentCourseModel, Integer> {
    StudentCourseModel findByStudentAndCourse(StudentModel student, CourseModel course);
}
