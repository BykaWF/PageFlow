/**
 * StudentController is a REST controller responsible for handling student-related endpoints.
 */
package com.project.pageflow.controller;

import com.project.pageflow.confing.jwt.TokenProvider;
import com.project.pageflow.dto.StudentInfoResponse;
import com.project.pageflow.models.SecuredUser;
import com.project.pageflow.models.Student;
import com.project.pageflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
    private TokenProvider tokenProvider;
    private UserService userService;

    /**
     * Constructs a StudentController instance with the required dependencies.
     * @param tokenProvider The TokenProvider instance.
     * @param userService The UserService instance.
     */
    @Autowired
    public StudentController(TokenProvider tokenProvider, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    /**
     * Endpoint to retrieve information about the currently authenticated student.
     * @param authorization The JWT token in the Authorization header.
     * @return ResponseEntity with the student information response.
     */
    @GetMapping("/info")
    public ResponseEntity<?> getInfoAboutStudent(@RequestHeader("Authorization") String authorization){
        String username = tokenProvider.getUsername(authorization.substring(7));
        SecuredUser user = (SecuredUser) userService.loadUserByUsername(username);
        Student student = user.getStudent();

        StudentInfoResponse studentInfoResponse = new StudentInfoResponse();
        studentInfoResponse.setName(student.getName());
        studentInfoResponse.setEmail(student.getEmail());
        studentInfoResponse.setRollNumber(student.getRollNumber());

        return new ResponseEntity<>(studentInfoResponse, HttpStatus.OK);
    }
}
