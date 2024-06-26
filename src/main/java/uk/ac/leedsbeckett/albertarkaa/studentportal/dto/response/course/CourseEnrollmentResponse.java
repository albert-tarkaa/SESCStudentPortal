package uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseEnrollmentResponse {
    private boolean success;
    private String errorMessage;
    private InvoiceData data;
}
