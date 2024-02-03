package com.project.pageflow.confing.jwt;

import com.project.pageflow.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.util.StringUtils.*;

public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserService userService;

    /**
     * Constructs a JwtFilter with the provided TokenProvider and UserService.
     * @param tokenProvider The TokenProvider instance to be used for JWT operations.
     * @param userService The UserService instance to be used for loading user details.
     */
    @Autowired
    public JwtFilter(TokenProvider tokenProvider, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }
    /**
     * Performs JWT token validation and user authentication for incoming HTTP requests.
     * If a valid token is found, it sets the authentication details in the Spring Security context.
     * @param request The HTTP request object.
     * @param response The HTTP response object.
     * @param filterChain The filter chain to continue processing the request.
     * @throws ServletException If an error occurs during the filter processing.
     * @throws IOException If an I/O error occurs during the filter processing.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getJwtFromRequest(request);
        if(hasText(token) && tokenProvider.validate(token)){
            String username = tokenProvider.getUsername(token);

            UserDetails userDetails = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request,response);
    }
    /**
     * Extracts the JWT token from the Authorization header of the HTTP request.
     * @param request The HTTP request object.
     * @return The extracted JWT token as a String, or null if not found.
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String headerToken = request.getHeader("Authorization");
        if(hasText(headerToken) && headerToken.startsWith("Bearer ") ){
            return headerToken.substring(7);
        }

        return null;
    }
}
