package uk.ac.leedsbeckett.albertarkaa.studentportal.service;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.client.RestTemplate;
import uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.ControllerResponse;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.CourseModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.StudentModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.UserModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.repository.CourseRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.repository.StudentCourseRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.repository.StudentRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.repository.UserRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.util.Authentication.AuthServiceImplementation;
import uk.ac.leedsbeckett.albertarkaa.studentportal.util.Authentication.AuthenticationUtility;
import uk.ac.leedsbeckett.albertarkaa.studentportal.util.Authentication.Role;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@DataJpaTest
@ImportAutoConfiguration
class CourseServiceTest {

    String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0YXZpIiwiaWF0IjoxNzEzOTU3NDQ3LCJleHAiOjE3MTQzMTc0NDd9" +
            ".N6S8D6UsE2mwzsjrIfyC5PHMpUQ3A_hW9-s4FOc65eQ";

    @Value("${libraryUrl}")
    private String libraryURL;


    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentCourseRepository studentCourseRepository;

    @Mock
    private AuthServiceImplementation authServiceImplementation;


    @Autowired
    private UserRepository userRepository;

    @Mock
    private AuthenticationUtility authenticationUtility;

    @Mock
    private RestTemplate restTemplate;

    private CourseService courseService;

    private UserModel userModel;
    private StudentModel student;

    @BeforeEach
    public void setup() {
        Faker faker = new Faker();
        userModel = UserModel.builder()
                .id((int) faker.number().randomNumber())
                .email(faker.internet().emailAddress())
                .username(faker.name().username())
                .password(faker.internet().password())
                .createdAt(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .role(Role.STUDENT)
                .studentModel(null)
                .build();


        userRepository.save(userModel);

        student = StudentModel.builder()
                .id(userModel.getId())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(userModel.getEmail())
                .studentID("c" + faker.number().digits(7))
                .studentCourses(null)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        studentRepository.save(student);

        token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0YXZpIiwiaWF0IjoxNzEzOTU3NDQ3LCJleHAiOjE3MTQzMTc0NDd9" +
                ".N6S8D6UsE2mwzsjrIfyC5PHMpUQ3A_hW9-s4FOc65eQ";
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void enrollCourse() {
        // Arrange
        CourseModel course = CourseModel.builder()
                .courseCode("SESC501")
                .courseName(new Faker().lorem().sentence(5))
                .courseDescription(new Faker().lorem().sentence(25))
                .fee(new Faker().number().randomDouble(2, 50, 150))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        courseRepository.save(course);

        CourseService courseService = new CourseService(courseRepository, studentRepository, studentCourseRepository,
                authServiceImplementation, authenticationUtility);

        when(authenticationUtility.authorizeUser(token, userModel.getId()))
                .thenReturn(new ControllerResponse<>(true, null, null));
        when(courseRepository.findByCourseCode("SESC502"))
                .thenReturn(course);
    }
}