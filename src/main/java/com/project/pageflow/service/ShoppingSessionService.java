package com.project.pageflow.service;

import com.project.pageflow.models.ShoppingSession;
import com.project.pageflow.models.Student;
import com.project.pageflow.repository.ShoppingSessionRepository;
import com.project.pageflow.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShoppingSessionService {

    private ShoppingSessionRepository shoppingSessionRepository;
    private StudentRepository studentRepository;

    public void openOrUpdateShoppingSessionForStudent(Student student){

        if (student.getShoppingSession() == null) {

            ShoppingSession shoppingSession = new ShoppingSession();
            shoppingSession.setStudent(student);
            student.setShoppingSession(shoppingSession);

            shoppingSessionRepository.save(shoppingSession);
        }

        studentRepository.save(student);

    }
}
