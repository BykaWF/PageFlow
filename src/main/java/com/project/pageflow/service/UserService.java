package com.project.pageflow.service;

import com.project.pageflow.models.SecuredUser;
import com.project.pageflow.repository.UserRepository;
import com.project.pageflow.util.authoritiesListProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.project.pageflow.util.Constant.INVALID_USER_TYPE;

public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public SecuredUser save(SecuredUser securedUser, String userType) {
        String encryptedPassword = passwordEncoder.encode(securedUser.getPassword());
        String authorities = authoritiesListProvider.getAuthorities(userType);

        if(authorities.equals(INVALID_USER_TYPE)) {
            return new SecuredUser();
        }

        securedUser.setAuthorities(authorities);
        securedUser.setPassword(encryptedPassword);
        return userRepository.save(securedUser);

    }

    public boolean isUsernameAvailable(SecuredUser securedUser) {
        return loadUserByUsername(securedUser.getUsername()) == null;
    }
}
