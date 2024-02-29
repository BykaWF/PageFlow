package com.project.pageflow.service;

import com.project.pageflow.models.CartItem;
import com.project.pageflow.models.ShoppingSession;
import com.project.pageflow.models.Student;
import com.project.pageflow.repository.ShoppingSessionRepository;
import com.project.pageflow.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ShoppingSessionService {

    private ShoppingSessionRepository shoppingSessionRepository;
    private StudentRepository studentRepository;

    public void openOrUpdateShoppingSessionForStudent(Student student) {

        if (student.getShoppingSession() == null) {

            ShoppingSession shoppingSession = new ShoppingSession();
            shoppingSession.setStudent(student);
            student.setShoppingSession(shoppingSession);

            shoppingSessionRepository.save(shoppingSession);
        }

        studentRepository.save(student);

    }

    public void updateTotalOfCurrentStudent(ShoppingSession currentShoppingSession) {
        if (currentShoppingSession != null && currentShoppingSession.getCartItems() != null) {
            List<CartItem> cartItems = currentShoppingSession.getCartItems();

            BigDecimal totalPrice = cartItems.stream()
                    .map(item -> item.getBook().getPrice().multiply(new BigDecimal(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            currentShoppingSession.setTotal(totalPrice);
            shoppingSessionRepository.saveAndFlush(currentShoppingSession);
        } else {
            throw new IllegalArgumentException("Invalid shopping session");
        }
    }
}