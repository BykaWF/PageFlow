/**
 * JwtEntryPoint is a component responsible for handling authentication errors and sending an unauthorized response.
 * It implements the AuthenticationEntryPoint interface from Spring Security.
 */
package com.project.pageflow.confing.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {
    /**
     * Handles authentication errors by sending an unauthorized response with the corresponding error message.
     * @param request The HTTP request object.
     * @param response The HTTP response object.
     * @param authException The authentication exception that occurred.
     * @throws IOException If an I/O error occurs while sending the response.
     * @throws ServletException If an error occurs during the servlet processing.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
