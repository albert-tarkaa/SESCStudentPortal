package uk.ac.leedsbeckett.albertarkaa.studentportal.Controller.student;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Service.StudentService;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Utils.ControllerResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/student/{id}")
    public ResponseEntity<ControllerResponse<StudentResponse>> getStudent(@PathVariable int id) {
        ControllerResponse<StudentResponse> response = studentService.getStudent(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }


    @PostMapping("/student/update/{id}")
    public ResponseEntity<ControllerResponse<StudentResponse>> updateStudent(@PathVariable("id") int id, @RequestBody StudentUpdateRequest studentUpdateRequest
    ) {

        ControllerResponse<StudentResponse> response = studentService.updateStudent(id, studentUpdateRequest);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }

    }

}
