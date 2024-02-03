/**
 * SecurityConfig is a configuration class responsible for defining security-related configurations for the application.
 */
package com.project.pageflow.confing;

import com.project.pageflow.confing.jwt.JwtEntryPoint;
import com.project.pageflow.confing.jwt.JwtFilter;
import com.project.pageflow.confing.jwt.TokenProvider;
import com.project.pageflow.repository.UserRepository;
import com.project.pageflow.service.UserService;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Arrays;
import java.util.List;

import static com.project.pageflow.util.Constant.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtEntryPoint jwtEntryPoint;
    private final UserRepository userRepository;
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    /**
     * Constructs a SecurityConfig instance with JwtEntryPoint and UserRepository dependencies.
     * @param jwtEntryPoint The JwtEntryPoint instance.
     * @param userRepository The UserRepository instance.
     */

    public SecurityConfig(JwtEntryPoint jwtEntryPoint, UserRepository userRepository) {
        this.jwtEntryPoint = jwtEntryPoint;
        this.userRepository = userRepository;
    }

    /**
     * Defines the security filter chain for HTTP security configurations.
     * @param httpSecurity The HttpSecurity instance.
     * @return A SecurityFilterChain instance.
     * @throws Exception If an error occurs during the security filter chain configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/api/v1/auth/*").permitAll()
                                .requestMatchers("/api/v1/student/info").hasAuthority(STUDENT_SELF_INFO_AUTHORITY)
                                .requestMatchers("/admin/**").hasAuthority(CREATE_ADMIN_AUTHORITY)
                                .requestMatchers("/profile").hasAuthority(STUDENT_SELF_INFO_AUTHORITY)
                                .requestMatchers("/book/**").hasAuthority(CREATE_BOOK_AUTHORITY)
                                .requestMatchers("/transaction/**").hasAuthority(INITIATE_TRANSACTION_AUTHORITY)
                )
                .addFilterBefore(jwtFilter(),UsernamePasswordAuthenticationFilter.class)
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();
    }

    /**
     * Configures and initializes the UserService bean.
     *
     * @return An instance of the UserService initialized with UserRepository and PasswordEncoder.
     */
    @Bean
    public UserService userService(){
        return new UserService(userRepository,passwordEncoder());
    }

    /**
     * Configures and initializes the TokenProvider bean.
     *
     * @return An instance of the TokenProvider.
     */
    @Bean
    public TokenProvider tokenProvider(){
        return new TokenProvider();
    }

    /**
     * Configures and initializes the JwtFilter bean.
     *
     * @return An instance of the JwtFilter initialized with TokenProvider and UserService.
     */
    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter(tokenProvider(),userService());
    }

    /**
     * Defines the user details service bean.
     * @return A UserDetailsService instance.
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserService(userRepository,passwordEncoder());
    }

    /**
     * Defines the authentication manager bean.
     * @param userDetailsService The UserDetailsService instance.
     * @return An AuthenticationManager instance.
     */
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
        var authProvider = authenticationProvider();
        return new ProviderManager(authProvider);
    }

    /**
     * Defines the authentication provider bean.
     * @return An AuthenticationProvider instance.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * Defines the password encoder bean.
     * @return A PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines the CORS filter bean.
     * @return A CorsFilter instance.
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://127.0.0.1:5500")); // Add your frontend origin here
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * Defines the secret key bean for JWT token operations.
     * @return A SecretKey instance.
     */
    @Bean
    public SecretKey setKey(){
        byte[] decodedKey = java.util.Base64.getDecoder().decode(SECRET_KEY);
        return new SecretKeySpec(decodedKey,0,decodedKey.length,"HMACSHA256");
    }
}
