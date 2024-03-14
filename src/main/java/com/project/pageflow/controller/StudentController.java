/**
 * StudentController is a REST controller responsible for handling student-related endpoints.
 */
package com.project.pageflow.controller;


import com.project.pageflow.models.SecuredUser;
import com.project.pageflow.models.Student;
import com.project.pageflow.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
public class StudentController {

    private UserService userService;


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
