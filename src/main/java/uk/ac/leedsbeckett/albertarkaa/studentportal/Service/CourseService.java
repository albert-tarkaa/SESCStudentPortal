package uk.ac.leedsbeckett.albertarkaa.studentportal.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.course.CourseResponse;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.CourseModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.StudentCourseModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Model.StudentModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Repository.CourseRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Repository.StudentCourseRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Repository.StudentRepository;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Utils.ControllerResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;

    public ControllerResponse<List<CourseResponse>> getAllCourses() {
        try {
            List<CourseModel> courseModels = courseRepository.findAll();
            List<CourseResponse> courseResponses = courseModels.stream()
                    .map(this::mapCourseModelToResponse)
                    .collect(Collectors.toList());
            return new ControllerResponse<>(true, null, courseResponses);
        } catch (Exception e) {
            return new ControllerResponse<>(false, e.getMessage(), null);
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

    public ControllerResponse<String> enrollCourse(String courseCode, String studentId) {
        try {

            CourseModel courseModel = courseRepository.findByCourseCode(courseCode);
            if (courseModel == null) {
                return new ControllerResponse<>(false, "Course not found", null);
            }

            StudentModel studentModel = studentRepository.findByStudentID(studentId);
            if (studentModel == null || !studentModel.isUpdated()) {
                return new ControllerResponse<>(false, "Student not found/Student profile not updated. Ensure correct information and try again", null);
            }

            StudentCourseModel studentCourseModel = studentCourseRepository.findByStudentAndCourse(studentModel, courseModel);
            if (studentCourseModel != null) {
                return new ControllerResponse<>(false, "You are already enrolled in this course", null);
            }

            StudentCourseModel enrollment = StudentCourseModel.builder()
                    .student(studentModel)
                    .course(courseModel)
                    .enrolledAt(LocalDateTime.now())
                    .build();

            studentCourseRepository.save(enrollment);
            return new ControllerResponse<>(true, null,
                    "You have been enrolled successfully in " + courseModel.getCourseName());
        } catch (Exception e) {
            return new ControllerResponse<>(false, e.getMessage(), null);
        }
    }
}