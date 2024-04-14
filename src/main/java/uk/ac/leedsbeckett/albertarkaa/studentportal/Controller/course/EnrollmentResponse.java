package uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentResponse {
    private String courseCode;
    private String courseName;
    private String courseDescription;
    private double fee;
    private String reference;

    public EnrollmentResponse(EnrollmentResponse enrollmentResponse) {
        this.courseCode = enrollmentResponse.getCourseCode();
        this.courseName = enrollmentResponse.getCourseName();
        this.courseDescription = enrollmentResponse.getCourseDescription();
        this.fee = enrollmentResponse.getFee();
        this.reference = enrollmentResponse.getReference();
    }
}
