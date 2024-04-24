package uk.ac.leedsbeckett.albertarkaa.studentportal.dto.request.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.ac.leedsbeckett.albertarkaa.studentportal.controller.course.Account;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseEnrollmentRequest {
    private double amount;
    @Builder.Default
    private String type ="TUITION_FEES";
    private String studentId;
}

