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
@Table(name = "courses")
public class CourseModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String courseID;
    private String courseName;
    @Column(updatable = false)
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
