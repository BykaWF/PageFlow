package com.project.pageflow.service;

import com.project.pageflow.dto.CreateAdminRequest;
import com.project.pageflow.models.Admin;
import com.project.pageflow.models.SecuredUser;
import com.project.pageflow.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private AdminService adminService;

    @Test
    void itShouldCreateAdmin() {
        //Given
        CreateAdminRequest createAdminRequest = CreateAdminRequest.builder()
                .name("Admin Name")
                .email("admin@example.com")
                .username("adminUser")
                .password("adminPass")
                .build();

        Admin admin = createAdminRequest.toAdmin();

        SecuredUser securedUser = new SecuredUser();
        securedUser.setUsername(createAdminRequest.getUsername());
        securedUser.setPassword(createAdminRequest.getPassword());

        //When
        when(userService.save(any(SecuredUser.class),any(String.class))).thenReturn(securedUser);
        adminService.createAdmin(admin);
        //Then
        verify(userService).save(any(SecuredUser.class), any(String.class));
        verify(adminRepository).save(eq(admin));

        ArgumentCaptor<Admin> adminCaptor = ArgumentCaptor.forClass(Admin.class);
        verify(adminRepository).save(adminCaptor.capture());
        Admin savedAdmin = adminCaptor.getValue();
        assertEquals("Admin Name",savedAdmin.getName());
        assertEquals("admin@example.com",savedAdmin.getEmail());
        assertEquals("adminUser",savedAdmin.getSecuredUser().getUsername());
        assertEquals("adminPass",savedAdmin.getSecuredUser().getPassword());
    }

    @Test
    void itShouldFind() {
        //Given
        CreateAdminRequest createAdminRequest = CreateAdminRequest.builder()
                .name("Admin Name")
                .email("admin@example.com")
                .username("adminUser")
                .password("adminPass")
                .build();

        Admin admin = createAdminRequest.toAdmin();
        admin.setId(1);

        SecuredUser securedUser = new SecuredUser();
        securedUser.setUsername(createAdminRequest.getUsername());
        securedUser.setPassword(createAdminRequest.getPassword());
        //When
        when(adminService.find(1)).thenReturn(Optional.of(admin));

        //Then

        Optional<Admin> foundAdminOptional = adminService.find(1);
        assertTrue(foundAdminOptional.isPresent(),"Admin should be present");
        Admin foundAdmin = foundAdminOptional.get();
        assertEquals(1,foundAdmin.getId());
        assertEquals("Admin Name",foundAdmin.getName());
        assertEquals("admin@example.com",foundAdmin.getEmail());
    }
}