package com.project.pageflow.service;

import com.project.pageflow.dto.CartItemDto;
import com.project.pageflow.models.Book;
import com.project.pageflow.models.CartItem;
import com.project.pageflow.models.ShoppingSession;
import com.project.pageflow.models.Student;
import com.project.pageflow.repository.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CartItemService {

    private CartItemRepository cartItemRepository;
    private BookService bookService;

    @Transactional
    public void addOrUpdateCartItemForStudent(CartItemDto cartItemDto, Student student) {
        Book book = getBookById(cartItemDto.getBookId());
        ShoppingSession currentShoppingSession = student.getShoppingSession();

        ensureShoppingSessionExists(currentShoppingSession);

        if (cartItemRepository.isBookAlreadyInCart(currentShoppingSession, book)) {
            updateExistingCartItem(book, cartItemDto.getQuantity());
        } else {
            addNewCartItem(cartItemDto, book, currentShoppingSession, cartItemDto.getQuantity());
        }
    }

    private void addNewCartItem(CartItemDto cartItemDto, Book book, ShoppingSession currentShoppingSession, Integer quantity) {
        CartItem cartItem = cartItemDto.toItem();
        cartItem.setBook(book);
        cartItem.setShoppingSession(currentShoppingSession);
        setSubtotalForBook(cartItem, book);
        cartItemRepository.save(cartItem);

    }

    private void ensureShoppingSessionExists(ShoppingSession currentShoppingSession) {
        if (currentShoppingSession == null) {
            throw new IllegalStateException("Shopping session not found for student");
        }
    }

    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.delete(Objects.requireNonNull(cartItemRepository.findById(cartItemId).orElse(null)));
    }

    public List<CartItem> getCartItems(Long sessionId) {
        return cartItemRepository.findCartItemByShoppingSessionId(sessionId);
    }

    private Book getBookById(Integer bookId) {
        return bookService.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
    }

    private void setSubtotalForBook(CartItem cartItem, Book book) {
        cartItem.setSubtotal(book.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
    }

    public void updateExistingCartItem(Book book, Integer quantity) {
        CartItem cartItemByBook = cartItemRepository.findCartItemByBook(book);
        cartItemByBook.setQuantity(quantity);
        setSubtotalForBook(cartItemByBook, book);
        cartItemRepository.save(cartItemByBook);
    }

}
