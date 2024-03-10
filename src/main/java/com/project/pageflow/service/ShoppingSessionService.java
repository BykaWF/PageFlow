package com.project.pageflow.service;

import com.project.pageflow.models.CartItem;
import com.project.pageflow.models.ShoppingCartInfo;
import com.project.pageflow.models.ShoppingSession;
import com.project.pageflow.models.Student;
import com.project.pageflow.repository.ShoppingSessionRepository;
import com.project.pageflow.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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
            setDefaultTotal(shoppingSession);

            shoppingSessionRepository.save(shoppingSession);
        }

        studentRepository.save(student);

    }

    public ShoppingCartInfo getShoppingCartInfo(Student student) {
        Long shoppingSessionId = student.getShoppingSession().getId();
        List<CartItem> cartItems = cartItemService.getCartItems(shoppingSessionId);
        ShoppingSession updatedShoppingSession = updateTotalOfCurrentSession(student.getShoppingSession());

        return new ShoppingCartInfo(cartItems, updatedShoppingSession);
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

    public void cleanup(ShoppingSession shoppingSession) {
        setDefaultTotal(shoppingSession);
    }

    public void setDefaultTotal(ShoppingSession shoppingSession){
        shoppingSession.setTotal(new BigDecimal("0.00"));
    }
}