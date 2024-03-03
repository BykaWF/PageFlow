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
            shoppingSession.setTotal(new BigDecimal("0.00"));

            shoppingSessionRepository.save(shoppingSession);
        }

        studentRepository.save(student);

    }


    public ShoppingSession updateTotalOfCurrentSession(ShoppingSession currentShoppingSession) {
        List<CartItem> cartItems = cartItemService.getCartItems(currentShoppingSession.getId());
        currentShoppingSession.setTotal(getTotal(cartItems));
        shoppingSessionRepository.save(currentShoppingSession);
        return currentShoppingSession;
    }

    public BigDecimal getTotal(List<CartItem> cartItems){
       return cartItems.stream().map(CartItem::getSubtotal).reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}