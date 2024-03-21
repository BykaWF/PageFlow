package com.project.pageflow.service;

import com.project.pageflow.excetption.UsernameIsNotAvailableException;
import com.project.pageflow.models.Student;
import com.project.pageflow.repository.StudentRepository;
import com.project.pageflow.models.SecuredUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.project.pageflow.util.Constant.STUDENT_USER;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserService userService;

    public StudentService(StudentRepository studentRepository,
                          UserService userService) {
        this.studentRepository = studentRepository;
        this.userService = userService;

    }

    public Student getCurrentStudent(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof SecuredUser) {
            return ((SecuredUser) principal).getStudent();
        }
        return null;

    }

    public void createStudent(Student student) {
        // if student do not exist already in db with this password an
        SecuredUser securedUser = student.getSecuredUser();
        if (userService.isUsernameAvailable(securedUser)) {
            securedUser = userService.save(securedUser, STUDENT_USER);
            student.setSecuredUser(securedUser);
            studentRepository.save(student);
        } else {
            throw new UsernameIsNotAvailableException(securedUser.getUsername() + " is not available !");
        }
    }


    public Optional<Student> find(Integer studentId) {
        return studentRepository.findById(studentId);
    }

    public void updateStudent(Student student) {
        studentRepository.save(student);
    }
}
