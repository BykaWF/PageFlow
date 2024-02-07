/**
 * StudentController is a REST controller responsible for handling student-related endpoints.
 */
package com.project.pageflow.controller;


import com.project.pageflow.dto.StudentInfoResponse;
import com.project.pageflow.models.SecuredUser;
import com.project.pageflow.models.Student;
import com.project.pageflow.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@AllArgsConstructor
@Controller
public class StudentController {

    private UserService userService;

    /**
     * Endpoint to retrieve information about the currently authenticated student.
     * @return ResponseEntity with the student information response.
     */
    @GetMapping("/student-info")
    @PreAuthorize("isAuthenticated()")
    public String getInfoAboutStudent(Model model, Authentication authentication){
        Object principal = authentication.getPrincipal();
        if(principal instanceof SecuredUser){
            try {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                SecuredUser user = (SecuredUser) userService.loadUserByUsername(username);
                Student student = user.getStudent();
                model.addAttribute("student",student);
                return "student-info";
            } catch (BadCredentialsException e){
                model.addAttribute("BadCredentials", e);
                return "login";
            }
        }else {
            return "login";
        }

    }
}
