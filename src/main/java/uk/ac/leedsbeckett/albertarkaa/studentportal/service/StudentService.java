package uk.ac.leedsbeckett.albertarkaa.studentportal.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.ac.leedsbeckett.albertarkaa.studentportal.controller.course.CourseInfo;
import uk.ac.leedsbeckett.albertarkaa.studentportal.dto.request.student.StudentUpdateRequest;
import uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.ControllerResponse;
import uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.student.GraduationResponse;
import uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.student.StudentResponse;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.CourseModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.StudentCourseModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.StudentModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.UserModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.repository.StudentRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.util.Authentication.AuthenticationUtility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private final StudentRepository studentRepository;
    private final AuthenticationUtility authenticationUtility;
    @Value("${financeUrl}")
    private String financeURL;

    public StudentModel createStudentFromUser(UserModel user) {
        if (studentRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Student already exists");
        }
        StudentModel student = new StudentModel();
        student.setEmail(user.getEmail());
        student.setStudentID("c7" + RandomStringUtils.randomNumeric(6));
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        return studentRepository.save(student);
    }

    public ControllerResponse<StudentResponse> getStudent(int id, String token) {
        try {

            ControllerResponse<?> response = authenticationUtility.authorizeUser(token, id);
            if (!response.isSuccess()) {
                // User is not authorized, return the authResponse
                return new ControllerResponse<>(false, response.getErrorMessage(), null);
            }

            StudentModel studentOptional = studentRepository.findById(id);
            if (studentOptional != null) {

                List<CourseInfo> courses = new ArrayList<>();
                if (studentOptional.getStudentCourses() != null){
                    for (StudentCourseModel courseModel : studentOptional.getStudentCourses()) {
                        CourseModel course = courseModel.getCourse();
                        courses.add(new CourseInfo(course, courseModel.getReference()));
                    }
                }

                return new ControllerResponse<>(true, null, StudentResponse.builder()
                        .id(studentOptional.getId())
                        .studentID(studentOptional.getStudentID())
                        .firstName(studentOptional.getFirstName())
                        .lastName(studentOptional.getLastName())
                        .email(studentOptional.getEmail())
                        .courses(courses)
                        .build());
            } else {
                throw new NotFoundException();
            }
        } catch (Exception e) {
            logger.error("An error occurred", e);
            return new ControllerResponse<>(false, "An unexpected error occurred while processing your request. Please try again later.", null);

        }
    }

    public ControllerResponse<StudentResponse> updateStudent(int id, StudentUpdateRequest studentUpdateRequest, String token) {
        try {

            ControllerResponse<?> response = authenticationUtility.authorizeUser(token, id);
            if (!response.isSuccess()) {
                // User is not authorized, return the authResponse
                return new ControllerResponse<>(false, response.getErrorMessage(), null);
            }

            StudentModel studentOptional = studentRepository.findById(id);
            if (studentOptional != null) {
                if (studentUpdateRequest == null || studentUpdateRequest.getFirstName() == null || studentUpdateRequest.getLastName() == null)
                    return new ControllerResponse<>(false, "Invalid request: First name and last name are required", null);

                updateStudentDetails(studentOptional, studentUpdateRequest);
                return new ControllerResponse<>(true, null, new StudentResponse(studentOptional));
            } else return new ControllerResponse<>(false, "Student not found", null);

        } catch (Exception e) {
            logger.error("An error occurred", e);
            return new ControllerResponse<>(false, "An unexpected error occurred while processing your request. Please try again later.", null);

        }
    }

    private void updateStudentDetails(StudentModel studentToUpdate, StudentUpdateRequest studentUpdateRequest) {
        studentToUpdate.setFirstName(studentUpdateRequest.getFirstName());
        studentToUpdate.setLastName(studentUpdateRequest.getLastName());
        studentToUpdate.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(studentToUpdate);
    }

    public ControllerResponse<Object> getGraduationStatus(int id, String token) {
        try {
            ControllerResponse<?> response = authenticationUtility.authorizeUser(token, id);
            if (!response.isSuccess()) {
                // User is not authorized, return the authResponse
                return new ControllerResponse<>(false, response.getErrorMessage(), null);
            }

            StudentModel studentOptional = studentRepository.findById(id);
            if (studentOptional != null) {

                RestTemplate restTemplate = new RestTemplate();

                GraduationResponse gradResponse =
                        restTemplate.getForObject(financeURL + "/accounts/" + studentOptional.getStudentID(), GraduationResponse.class);


                if (gradResponse != null && gradResponse.isSuccess() && gradResponse.getData().isHasOutstandingBalance()) {
                    return new ControllerResponse<>(true, null, "You have an outstanding balance. Please clear your balance to graduate.");
                } else {
                    return new ControllerResponse<>(true, null, "You are eligible to graduate.");
                }
            } else {
                throw new NotFoundException();
            }
        } catch (Exception e) {
            logger.error("An error occurred", e);
            return new ControllerResponse<>(false, "An unexpected error occurred while processing your request. Please try again later.", null);

        }


    }
}
