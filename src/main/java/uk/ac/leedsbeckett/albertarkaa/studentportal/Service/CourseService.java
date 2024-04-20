package uk.ac.leedsbeckett.albertarkaa.studentportal.Service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.course.*;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.CourseModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.StudentCourseModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.StudentModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.UserModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Repository.CourseRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Repository.StudentCourseRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Repository.StudentRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Utils.Authentication.AuthServiceImplementation;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Utils.Authentication.AuthenticationUtility;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Utils.ControllerResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    @Value("${financeUrl}")
    private String financeURL;

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final AuthServiceImplementation authServiceImplementation;
    private final AuthenticationUtility authenticationUtility;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public ControllerResponse<List<CourseResponse>> getAllCourses(String token,String CourseName){
        try {
            Optional<UserModel> userOptional = authServiceImplementation.getUserByToken(token);
            if (userOptional.isEmpty()) {
                return new ControllerResponse<>(false, "User not authorised", null);
            }

            List<CourseModel> courseModels;
            if (CourseName == null || CourseName.isBlank()) {
                courseModels = courseRepository.findAll();
            } else {
                courseModels = courseRepository.findByCourseNameContainingIgnoreCase(CourseName);
            }

            List<CourseResponse> courseResponses = courseModels.stream()
                    .map(this::mapCourseModelToResponse)
                    .collect(Collectors.toList());
            return new ControllerResponse<>(true, null, courseResponses);
        } catch (Exception e) {
            logger.error("An error occurred", e);
            return new ControllerResponse<>(false,
                    "An unexpected error occurred while processing your request. Please try again later.",
                    null);

        }
    }

    private CourseResponse mapCourseModelToResponse(CourseModel courseModel) {
        return new CourseResponse(
                courseModel.getCourseCode(),
                courseModel.getCourseName(),
                courseModel.getCourseDescription(),
                courseModel.getFee()
        );
    }

    public ControllerResponse<EnrollmentResponse> enrollCourse(String courseCode, int id, String token) {
        try {
            ControllerResponse<?> response = authenticationUtility.authorizeUser(token, id);
            if (!response.isSuccess()) {
                // User is not authorized, return the authResponse
                return new ControllerResponse<>(false, response.getErrorMessage(), null);
            }

            CourseModel courseModel = courseRepository.findByCourseCode(courseCode);
            if (courseModel == null) {
                return new ControllerResponse<>(false, "Course not found", null);
            }

            StudentModel studentModel = studentRepository.findById(id);
            if (studentModel == null) {
                return new ControllerResponse<>(false, "Student not found", null);
            }

            StudentCourseModel studentCourseModel = studentCourseRepository.findByStudentAndCourse(studentModel, courseModel);
            if (studentCourseModel != null) {
                return new ControllerResponse<>(false, "You are already enrolled in this course", null);
            }

            CourseEnrollmentRequest courseEnrollmentRequest = new CourseEnrollmentRequest();
            courseEnrollmentRequest.setAmount(courseModel.getFee());

            Account account = new Account();
            account.setStudentId(studentModel.getStudentID());
            courseEnrollmentRequest.setAccount(account);


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<CourseEnrollmentRequest> AccountRequest = new HttpEntity<>(courseEnrollmentRequest,headers);


            CourseEnrollmentResponse enrollmentResponse =  restTemplate.postForObject(financeURL + "/invoices/",
                    AccountRequest, CourseEnrollmentResponse.class);

            StudentCourseModel enrollment = null;
            if (enrollmentResponse != null) {
                enrollment = StudentCourseModel.builder()
                        .student(studentModel)
                        .course(courseModel)
                        .reference(enrollmentResponse.getReference())
                        .enrolledAt(LocalDateTime.now())
                        .build();
            }

            EnrollmentResponse enrollmentResponseData = null;
            if (enrollment != null) {
                enrollmentResponseData = EnrollmentResponse.builder()
                        .courseCode(courseModel.getCourseCode())
                        .courseName(courseModel.getCourseName())
                        .courseDescription(courseModel.getCourseDescription())
                        .fee(courseModel.getFee())
                        .reference(enrollment.getReference())
                        .build();
            }


            if (enrollment != null) {
                studentCourseRepository.save(enrollment);
            }
            return new ControllerResponse<EnrollmentResponse>(true, null, new EnrollmentResponse(enrollmentResponseData));
        } catch (Exception e) {
            logger.error("An error occurred", e);
            return new ControllerResponse<>(false, "An unexpected error occurred while processing your request. Please try again later.", null);

        }
    }
}