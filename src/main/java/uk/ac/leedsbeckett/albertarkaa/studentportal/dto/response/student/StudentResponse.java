package uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.StudentModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.controller.course.CourseInfo;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private int id;
    private String studentID;
    private String email;
    private String firstName;
    private String lastName;
    private List<CourseInfo> courses;


    public StudentResponse(StudentModel studentToUpdate) {
        this.id = studentToUpdate.getId();
        this.studentID = studentToUpdate.getStudentID();
        this.email = studentToUpdate.getEmail();
        this.firstName = studentToUpdate.getFirstName();
        this.lastName = studentToUpdate.getLastName();
        this.courses = studentToUpdate.getStudentCourses().stream()
                .map(studentCourse -> new CourseInfo(studentCourse.getCourse(), studentCourse.getReference()))
                .collect(Collectors.toList());
    }
}
