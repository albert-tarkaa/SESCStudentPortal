package uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.CourseModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfo {
    private String courseCode;
    private String courseName;
    private String courseDescription;
    private double fee;

    public CourseInfo(CourseModel course) {
        this.courseCode = course.getCourseCode();
        this.courseName = course.getCourseName();
        this.courseDescription = course.getCourseDescription();
        this.fee = course.getFee();
    }
}
