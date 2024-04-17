package uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MicroservicesStudentIDRequest {
    private String StudentId;
}
