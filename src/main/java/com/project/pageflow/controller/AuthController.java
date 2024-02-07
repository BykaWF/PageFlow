/**
 * AuthController is a REST controller responsible for handling authentication-related endpoints.
 */
package com.project.pageflow.controller;

import com.project.pageflow.dto.LoginRequest;
import com.project.pageflow.dto.CreateStudentRequest;
import com.project.pageflow.dto.LoginResponse;
import com.project.pageflow.models.Student;
import com.project.pageflow.service.StudentService;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private StudentService studentService;

    /**
     * Constructs an AuthController instance with the required dependencies.
     * @param authenticationManager The AuthenticationManager instance.
     * @param studentService The StudentService instance.
     */
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Endpoint for user registration.
     *
     * @param createStudentRequest The CreateStudentRequest object containing registration details.
     * @param model                The Model object for adding attributes.
     * @return The view name indicating the registration status.
     */
    @PostMapping("/register")
    public String register(@Valid CreateStudentRequest createStudentRequest, Model model) {
        try {
            Student student = createStudentRequest.toStudent();
            studentService.createStudent(student);
            model.addAttribute("message", "Registration successful");
            return "registration-success";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", "User with this username/email already exists");
            return "sing-up";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred during registration");
            return "sing-up";
        }
    }
}
