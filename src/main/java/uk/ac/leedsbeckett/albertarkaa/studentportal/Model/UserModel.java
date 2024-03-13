package uk.ac.leedsbeckett.albertarkaa.studentportal.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    private String role;
    @Column(updatable = false)
    private LocalDate createdAt;
    private LocalDate lastLogin;


    public UserModel(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public UserModel(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = "student";
    }

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
        this.email = "";
        this.role = "student";
    }
}
