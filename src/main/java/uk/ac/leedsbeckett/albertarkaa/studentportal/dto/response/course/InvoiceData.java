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
public class InvoiceData {
    private long id;
    private String reference;
    private double amount;
    private LocalDate dueDate;
    private String type;
    private String status;
    private String studentId;
    private Map<String, Map<String, String>> _links;
}
