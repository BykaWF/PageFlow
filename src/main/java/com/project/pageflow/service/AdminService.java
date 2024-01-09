package com.project.pageflow.service;


import com.project.pageflow.models.SecuredUser;
import com.project.pageflow.repository.AdminRepository;
import com.project.pageflow.models.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.project.pageflow.util.Constant.ADMIN_USER;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    private final UserService userService;

    public AdminService(AdminRepository adminRepository, UserService userService) {
        this.adminRepository = adminRepository;
        this.userService = userService;
    }

    public void createAdmin(Admin admin) {
        SecuredUser securedUser = admin.getSecuredUser();
        securedUser = userService.save(securedUser, ADMIN_USER);

        admin.setSecuredUser(securedUser);
        adminRepository.save(admin);
    }

    public Optional<Admin> find(Integer adminId) {
        return adminRepository.findById(adminId);
    }

}
