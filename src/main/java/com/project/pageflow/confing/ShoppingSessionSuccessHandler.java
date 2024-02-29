package com.project.pageflow.confing;

import com.project.pageflow.models.SecuredUser;
import com.project.pageflow.models.Student;
import com.project.pageflow.service.ShoppingSessionService;
import com.project.pageflow.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@AllArgsConstructor
public class ShoppingSessionSuccessHandler implements AuthenticationSuccessHandler {

    private final ShoppingSessionService shoppingSessionService;
    private final StudentService studentService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

            try {

                Student currentStudent = studentService.getCurrentStudent(authentication);
                shoppingSessionService.openOrUpdateShoppingSessionForStudent(currentStudent);
                response.sendRedirect("/library");

            } catch (BadCredentialsException e) {
                throw new BadCredentialsException("BadCredentials");
            }

    }
}
