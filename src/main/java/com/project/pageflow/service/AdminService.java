package com.project.pageflow.service;


import com.project.pageflow.repository.AdminRepository;
import com.project.pageflow.models.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void createAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    public Admin find(Integer adminId) {
        return adminRepository.findById(adminId).orElse(null);
    }

}
