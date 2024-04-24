package uk.ac.leedsbeckett.albertarkaa.studentportal.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.StudentModel;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ImportAutoConfiguration
class StudentRepositoryTests {

    @Autowired
    private StudentRepository studentRepository;

    private StudentModel student;
    private StudentModel student1;

    @BeforeEach
    void setUp() {
        student = StudentModel.builder()
                .firstName("Albert")
                .lastName("Tarkaa")
                .email("albert@tarkaa.com")
                .studentID("1234567")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        student1 = StudentModel.builder()
                .firstName("Leonard")
                .lastName("Tarkaa")
                .email("leonard@tarkaa.com")
                .studentID("6789067")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void shouldSaveStudent() {
        // Save student
        StudentModel savedStudent = studentRepository.save(student);
        // Assert that saved student is not null
        assertThat(savedStudent).isNotNull();
    }

    @Test
    void shouldFindStudentByEmail() {
        // Save student
        studentRepository.save(student);
        // Find student by email
        Optional<StudentModel> foundStudent = studentRepository.findByEmail(student.getEmail());
        // Assert that student is present
        assertThat(foundStudent).isPresent();
        // Assert that student email is equal to the saved student email
        assertThat(foundStudent.get().getEmail()).isEqualTo(student.getEmail());
    }

    @Test
    void shouldExistsByEmail() {
        // Save student
        studentRepository.save(student);
        // Check if student exists by email
        boolean exists = studentRepository.existsByEmail(student.getEmail());
        // Assert that student exists
        assertThat(exists).isTrue();
    }

    @Test
    void shouldFindStudentById() {
        // Save student
        StudentModel savedStudent = studentRepository.save(student);
        // Find student by id
        StudentModel foundStudent = studentRepository.findById(savedStudent.getId());
        // Assert that student is present
        assertThat(foundStudent).isNotNull();
        // Assert that student id is equal to the saved student id
        assertThat(foundStudent.getId()).isEqualTo(savedStudent.getId());
    }

    @Test
    void shouldNotFindStudentByInvalidEmail() {
        //Find student by invalid email
        Optional<StudentModel> foundStudent = studentRepository.findByEmail("mail@email.com");
        // Assert that student is not present
        assertThat(foundStudent).isNotPresent();
    }

    @Test
    void shouldNotExistsByInvalidEmail() {
        // Check if student exists by invalid email
        boolean exists = studentRepository.existsByEmail("mail@email.com");
        // Assert that student does not exist
        assertThat(exists).isFalse();
    }

    @Test
    void shouldFindAllStudents() {
        // Save students
        studentRepository.save(student);
        studentRepository.save(student1);
        // Find all students
        Iterable<StudentModel> students = studentRepository.findAll();
        // Assert that students are present
        assertThat(students).hasSize(2);
    }
}