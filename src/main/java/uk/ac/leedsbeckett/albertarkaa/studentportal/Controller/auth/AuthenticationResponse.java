package uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String authToken;
    private Integer id;
    private String username;
    private String studentID;
}
