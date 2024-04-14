package uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseEnrollmentRequest {
    private double amount;
    private LocalDate dueDate=LocalDate.now();
    private String type ="TUITION_FEES";
    private Account account;
}

