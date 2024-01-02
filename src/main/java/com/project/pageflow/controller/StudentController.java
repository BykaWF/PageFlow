package com.project.pageflow.controller;

import com.project.pageflow.dto.CreateStudentRequest;
import com.project.pageflow.dto.SearchRequest;
import com.project.pageflow.models.Student;
import com.project.pageflow.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
   private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // create student
    @PostMapping("/student") // for admin
    public void createStudent(@RequestBody @Valid CreateStudentRequest createStudentRequest) {
        studentService.createStudent(createStudentRequest.toStudent());
    }

    //updating a student

    //findStudentByEmail

    //findStudentByRollNumber

    //findStudentByName
    @GetMapping("/student-by-id") //only for a student to view their info
    public List<Student> findStudent(@RequestBody @Valid SearchRequest searchRequest) throws Exception {
        return studentService.findStudent(searchRequest.getSearchKey(), searchRequest.getSearchValue());
    }

}
