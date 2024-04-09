package uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentRequest {

    private String studentId;
    private String courseCode;
}

