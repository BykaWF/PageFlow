package com.project.pageflow.controller;

import com.project.pageflow.dto.CreateAdminRequest;
import com.project.pageflow.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/admin")
    public void createAdmin(@RequestBody @Valid CreateAdminRequest createAdminRequest) {
        adminService.createAdmin(createAdminRequest.toAdmin());
    }



}
