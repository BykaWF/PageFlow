package com.project.pageflow.service;

import com.project.pageflow.models.CartItem;
import com.project.pageflow.models.ShoppingSession;
import com.project.pageflow.models.Student;
import com.project.pageflow.repository.ShoppingSessionRepository;
import com.project.pageflow.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ShoppingSessionService {

    private ShoppingSessionRepository shoppingSessionRepository;
    private StudentRepository studentRepository;
    private CartItemService cartItemService;


    public void openOrUpdateShoppingSessionForStudent(Student student) {

        if (student.getShoppingSession() == null) {

            ShoppingSession shoppingSession = new ShoppingSession();
            shoppingSession.setStudent(student);
            student.setShoppingSession(shoppingSession);

            shoppingSessionRepository.save(shoppingSession);
        }

        studentRepository.save(student);

    }

    public void updateTotalOfCurrentStudent(Student student) {
        if (student.getShoppingSession() != null) {

            List<CartItem> cartItems = cartItemService.getCartItems(student.getShoppingSession().getId());


            BigDecimal totalPrice = cartItems.stream()
                    .map(item -> item.getBook().getPrice().multiply(new BigDecimal(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            student.getShoppingSession().setTotal(totalPrice);

            shoppingSessionRepository.saveAndFlush(student.getShoppingSession());
        } else {
            throw new IllegalArgumentException("Invalid shopping session");
        }
    }

}