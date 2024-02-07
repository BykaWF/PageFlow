/**
 * SecurityConfig is a configuration class responsible for defining security-related configurations for the application.
 */
package com.project.pageflow.confing;

import com.project.pageflow.repository.UserRepository;
import com.project.pageflow.service.UserService;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import static com.project.pageflow.util.Constant.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final UserRepository userRepository;

    /**
     * Constructs a SecurityConfig instance with JwtEntryPoint and UserRepository dependencies.
     * @param userRepository The UserRepository instance.
     */
    public SecurityConfig(UserRepository userRepository) {
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
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/**").permitAll()
                                .requestMatchers("/sing-up").permitAll()
                                .requestMatchers("/static/**").permitAll()
                                .requestMatchers("/library").authenticated()
                                .requestMatchers("/student-info").authenticated()
                                .requestMatchers("/api/v1/student/info").hasAuthority(STUDENT_SELF_INFO_AUTHORITY)
                                .requestMatchers("/admin/**").hasAuthority(CREATE_ADMIN_AUTHORITY)
                                .requestMatchers("/profile").hasAuthority(STUDENT_SELF_INFO_AUTHORITY)
                                .requestMatchers("/book/**").permitAll()
                                .requestMatchers("/transaction/**").hasAuthority(INITIATE_TRANSACTION_AUTHORITY)
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/library",true)
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
