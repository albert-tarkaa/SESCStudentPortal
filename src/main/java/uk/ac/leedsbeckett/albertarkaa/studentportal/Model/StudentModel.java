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
@Table(name = "students")
public class StudentModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String studentID;
    private String firstName;
    private String lastName;
    private String email;
    @Column(updatable = false)
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public StudentModel(String studentID, String firstName, String lastName, String email) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

}
