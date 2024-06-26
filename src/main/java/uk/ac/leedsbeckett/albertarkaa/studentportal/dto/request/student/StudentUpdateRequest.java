package uk.ac.leedsbeckett.albertarkaa.studentportal.dto.request.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class StudentUpdateRequest {
    private String firstName;
    private String lastName;
}


