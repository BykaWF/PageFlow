package com.project.pageflow.controller;

import com.project.pageflow.dto.CreateStudentRequest;
import com.project.pageflow.dto.SearchRequest;
import com.project.pageflow.models.SecuredUser;
import com.project.pageflow.models.Student;
import com.project.pageflow.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create") // for admin
    public void createStudent(@RequestBody @Valid CreateStudentRequest createStudentRequest) {
        studentService.createStudent(createStudentRequest.toStudent());
    }

    @GetMapping("/getInfo") //only for a student to view their info
    public List<Student> findStudent(@RequestBody @Valid SearchRequest searchRequest) throws Exception {
        return studentService.findStudent(searchRequest.getSearchKey(), searchRequest.getSearchValue());
    }

    @GetMapping("/profile")
    public Optional<Student> findStudent() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser user = (SecuredUser) authentication.getPrincipal();
        Integer studentId = user.getStudent().getId();
        return studentService.find(studentId);
    }

}
