package uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.student;

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
public class StudentData {
    private long id;
    private String studentId;
    private boolean hasOutstandingBalance;
}
