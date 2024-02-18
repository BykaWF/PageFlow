package com.project.pageflow.confing;

import com.project.pageflow.models.SecuredUser;
import com.project.pageflow.models.Student;
import com.project.pageflow.service.ShoppingSessionService;
import com.project.pageflow.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@AllArgsConstructor
public class ShoppingSessionSuccessHandler implements AuthenticationSuccessHandler {

    private ShoppingSessionService shoppingSessionService;
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Object principal = authentication.getPrincipal();

        if(principal instanceof SecuredUser) {

            try {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                SecuredUser user = (SecuredUser) userService.loadUserByUsername(username);
                Student student = user.getStudent();

                shoppingSessionService.openOrUpdateShoppingSessionForStudent(student);

            } catch (BadCredentialsException e) {
                throw new BadCredentialsException("BadCredentials");
            }

        }
    }
}
