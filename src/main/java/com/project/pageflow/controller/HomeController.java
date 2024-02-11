package com.project.pageflow.controller;

import com.project.pageflow.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@AllArgsConstructor
@Controller
public class HomeController {

    private AuthenticationManager authenticationManager;
    private StudentService studentService;

    @GetMapping("/")
    public String getHomePage(){
        return "index";
    }

    @GetMapping("/about")
    public String getAboutPage(){
        return "about";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/sing-up")
    public String getSingUpPage(){
        return "sing-up";
    }

    @GetMapping("/registration-success")
    public String getRegistrationSuccess(){
        return "registration-success";
    }


}
