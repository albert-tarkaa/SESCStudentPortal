package uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinanceResponse {
    private int id;
    private String studentId;
    private boolean hasOutstandingBalance;
}

