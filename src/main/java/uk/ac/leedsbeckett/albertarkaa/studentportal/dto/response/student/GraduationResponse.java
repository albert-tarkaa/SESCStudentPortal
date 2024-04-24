package uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.course.InvoiceData;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GraduationResponse {
    private boolean success;
    private String errorMessage;
    private StudentData data;
}
