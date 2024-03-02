package com.project.pageflow.repository;

import com.project.pageflow.models.Book;
import com.project.pageflow.models.CartItem;
import com.project.pageflow.models.ShoppingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findCartItemByShoppingSessionId(Long sessionId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CartItem c WHERE c.shoppingSession = :session AND c.book = :book")
    boolean isBookInCartForSession(@Param("session") ShoppingSession shoppingSession, @Param("book") Book book);

    CartItem findCartItemByBook(Book book);
}
