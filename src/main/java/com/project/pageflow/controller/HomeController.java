package com.project.pageflow.controller;

import com.project.pageflow.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@AllArgsConstructor
@Controller
public class HomeController {

    private AuthenticationManager authenticationManager;
    private StudentService studentService;
    private static final String LIBRARY_VIEW = "library";
    private static final String INDEX_VIEW = "index";
    private static final String ABOUT_VIEW = "about";
    private static final String LOGIN_VIEW = "login";
    private static final String SIGN_UP_VIEW = "sign-up";
    private static final String REGISTRATION_SUCCESS_VIEW = "registration-success";

    @GetMapping("/")
    public String getHomePage(Authentication authentication) {
        return getView(authentication, INDEX_VIEW);
    }

    @GetMapping("/about")
    public String getAboutPage(Authentication authentication) {
        return getView(authentication, ABOUT_VIEW);
    }

    @GetMapping("/login")
    public String getLoginPage(Authentication authentication) {
        return getView(authentication, LOGIN_VIEW);
    }

    @GetMapping("/sign-up")
    public String getSignUpPage(Authentication authentication) {
        return getView(authentication, SIGN_UP_VIEW);
    }

    @GetMapping("/registration-success")
    public String getRegistrationSuccess(Authentication authentication) {
        return getView(authentication, REGISTRATION_SUCCESS_VIEW);
    }

    private String getView(Authentication authentication, String defaultView) {
        return authentication.isAuthenticated() ? LIBRARY_VIEW : defaultView;
    }


}
