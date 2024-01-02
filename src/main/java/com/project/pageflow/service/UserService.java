package com.project.pageflow.service;

import com.project.pageflow.models.SecuredUser;
import com.project.pageflow.repository.UserRepository;
import com.project.pageflow.util.Constant;
import com.project.pageflow.util.authoritiesListProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public SecuredUser save(SecuredUser securedUser, String userType) {
        String encryptedPassword = passwordEncoder.encode(securedUser.getPassword());
        String authorities = authoritiesListProvider.getAuthorities(userType);
        if(authorities.equals(Constant.INVALID_USER_TYPE)) {
            return new SecuredUser();
        }

        securedUser.setAuthorities(authorities);
        securedUser.setPassword(encryptedPassword);
        return userRepository.save(securedUser);

    }
}
