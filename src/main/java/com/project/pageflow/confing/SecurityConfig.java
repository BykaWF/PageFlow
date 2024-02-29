/**
 * SecurityConfig is a configuration class responsible for defining security-related configurations for the application.
 */
package com.project.pageflow.confing;

import com.project.pageflow.repository.StudentRepository;
import com.project.pageflow.repository.UserRepository;
import com.project.pageflow.service.ShoppingSessionService;
import com.project.pageflow.service.StudentService;
import com.project.pageflow.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final UserRepository userRepository;
    private final ShoppingSessionService shoppingSessionService;

    private final  StudentRepository studentRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository, ShoppingSessionService shoppingSessionService, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.shoppingSessionService = shoppingSessionService;

        this.studentRepository = studentRepository;
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
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/api/shop/**").permitAll()
                                .requestMatchers("/**").permitAll()
                                .requestMatchers("/sing-up").permitAll()
                                .requestMatchers("/static/**").permitAll()
                                .requestMatchers("/library").authenticated()
                                .requestMatchers("/student-info").authenticated()

                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/library",true)
                        .successHandler(shoppingSessionSuccessHandler())
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .build();
    }
    @Bean
    public ShoppingSessionSuccessHandler shoppingSessionSuccessHandler(){
        return new ShoppingSessionSuccessHandler(shoppingSessionService,studentService());
    }

    @Bean
    public StudentService studentService(){
        return new StudentService(studentRepository,userService());
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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
