package com.project.pageflow.repository;

import com.project.pageflow.models.Book;
import com.project.pageflow.models.CartItem;
import com.project.pageflow.models.CartItemsStatus;
import com.project.pageflow.models.ShoppingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findCartItemByShoppingSessionIdAndStatus(Long sessionId, CartItemsStatus status);
    CartItem findCartItemByBookAndStatus(Book book, CartItemsStatus status);
    void deleteAllByShoppingSessionId(Long shoppingSessionId);
    List<CartItem> findCartItemByTransactionId(String transactionId);
}
