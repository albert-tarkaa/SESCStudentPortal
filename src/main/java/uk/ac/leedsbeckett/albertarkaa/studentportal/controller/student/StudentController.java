package uk.ac.leedsbeckett.albertarkaa.studentportal.controller.student;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.leedsbeckett.albertarkaa.studentportal.dto.request.student.StudentUpdateRequest;
import uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.student.StudentResponse;
import uk.ac.leedsbeckett.albertarkaa.studentportal.service.StudentService;
import uk.ac.leedsbeckett.albertarkaa.studentportal.dto.response.ControllerResponse;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<ControllerResponse<StudentResponse>> getStudent(@PathVariable int id,
                                                                          @RequestHeader("Authorization") String token)
    {
        ControllerResponse<StudentResponse> response = studentService.getStudent(id, token);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ControllerResponse<StudentResponse>> updateStudent(@PathVariable("id") int id,
                                                                             @RequestBody StudentUpdateRequest studentUpdateRequest, @RequestHeader("Authorization") String token)
     {
        ControllerResponse<StudentResponse> response = studentService.updateStudent(id, studentUpdateRequest, token);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

    }

    @GetMapping("/getGraduationStatus/{id}")
    public ResponseEntity<ControllerResponse<Object>> getGraduationStatus(@PathVariable int id,
                                                                          @RequestHeader("Authorization") String token)
    {
        ControllerResponse<Object> response = studentService.getGraduationStatus(id, token);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }

}
