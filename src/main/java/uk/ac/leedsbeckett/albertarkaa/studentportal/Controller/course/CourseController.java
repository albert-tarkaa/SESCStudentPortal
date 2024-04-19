package uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.course;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Service.CourseService;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Utils.ControllerResponse;

import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    @GetMapping("/list")
    public ResponseEntity<ControllerResponse<List<CourseResponse>>> getAllCourses(@RequestHeader("Authorization") String token,
                                                                                  @RequestParam(required = false) String CourseName){
        ControllerResponse<List<CourseResponse>> response = courseService.getAllCourses(token,CourseName);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }

    @PostMapping("/enroll/{id}")
    public ResponseEntity<ControllerResponse<EnrollmentResponse>> enrollCourse(
            @RequestBody EnrollmentRequest enrollmentRequest,
            @PathVariable int id,
            @RequestHeader("Authorization") String token){
        ControllerResponse<EnrollmentResponse> response = courseService.enrollCourse(enrollmentRequest.getCourseCode(),
               id, token);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }

}
