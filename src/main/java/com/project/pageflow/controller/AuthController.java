/**
 * AuthController is a REST controller responsible for handling authentication-related endpoints.
 */
package com.project.pageflow.controller;

import com.project.pageflow.confing.jwt.TokenProvider;
import com.project.pageflow.dto.LoginRequest;
import com.project.pageflow.dto.CreateStudentRequest;
import com.project.pageflow.dto.LoginResponse;
import com.project.pageflow.models.Student;
import com.project.pageflow.service.StudentService;
import com.project.pageflow.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private StudentService studentService;
    private TokenProvider tokenProvider;
    private UserService userService;

    /**
     * Constructs an AuthController instance with the required dependencies.
     * @param authenticationManager The AuthenticationManager instance.
     * @param studentService The StudentService instance.
     * @param tokenProvider The TokenProvider instance.
     * @param userService The UserService instance.
     */
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, StudentService studentService, TokenProvider tokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.studentService = studentService;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    /**
     * Endpoint for user registration.
     * @param createStudentRequest The CreateStudentRequest object containing registration details.
     * @return ResponseEntity with the registration status.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody CreateStudentRequest createStudentRequest) {
        try {
            Student student = createStudentRequest.toStudent();
            studentService.createStudent(student);
            return ResponseEntity.ok().body("Registration successful");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User with this username/email already exists");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during registration");
        }
    }

    /**
     * Endpoint for user login.
     * @param loginRequest The LoginRequest object containing login credentials.
     * @return ResponseEntity with the login response.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            ));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = tokenProvider.generateToken(authentication);

            return ResponseEntity.ok(new LoginResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username/password");
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Your account is disabled");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during authentication");
        }
    }
}
