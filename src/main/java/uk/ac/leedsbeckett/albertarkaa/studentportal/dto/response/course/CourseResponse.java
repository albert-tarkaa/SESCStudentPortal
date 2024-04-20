package uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {
    private String courseCode;
    private String courseName;
    private String courseDescription;
    private double fee;
}
