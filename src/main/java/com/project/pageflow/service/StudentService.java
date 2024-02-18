package com.project.pageflow.service;

import com.project.pageflow.models.Student;
import com.project.pageflow.repository.StudentRepository;
import com.project.pageflow.models.SecuredUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.project.pageflow.util.Constant.STUDENT_USER;

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
        securedUser = userService.save(securedUser, STUDENT_USER);

        student.setSecuredUser(securedUser);
        studentRepository.save(student);
    }


    public Optional<Student> find(Integer studentId){
        return studentRepository.findById(studentId);
    }

    public List<Student> findByRollNumber(String rollNumber){
        return studentRepository.findStudentByRollNumber(rollNumber);
    }




}
