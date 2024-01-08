package com.project.pageflow.service;

import com.project.pageflow.models.Student;
import com.project.pageflow.repository.StudentRepository;
import com.project.pageflow.util.Constant;
import com.project.pageflow.models.SecuredUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserService userService;

    public StudentService(StudentRepository studentRepository, UserService userService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
    }

    public void createStudent(Student student) {
        SecuredUser securedUser = student.getSecuredUser();
        securedUser = userService.save(securedUser, Constant.STUDENT_USER);

        student.setSecuredUser(securedUser);
        studentRepository.save(student);
    }

    public List<Student> findStudent(String searchKey, String searchValue) throws Exception {
        switch (searchKey) {
            case "name":
                return studentRepository.findStudentByName(searchValue);
            case "email":
                return studentRepository.findStudentByEmail(searchValue);
            case "rollNumber":
                return studentRepository.findStudentByRollNumber(searchValue);
            case "age":
                return studentRepository.findStudentByAge(Integer.parseInt(searchValue));
            case "id": {
                Optional<Student> student = studentRepository.findById(Integer.parseInt(searchValue));
                return student.map(Arrays::asList).orElseGet(ArrayList::new);
            } default:
                throw new Exception("Search key is not valid " + searchKey);
        }
    }

    public Optional<Student> find(Integer studentId){
        return studentRepository.findById(studentId);
    }




}
