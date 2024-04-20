package uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibraryResponse {
    private String studentId;
}