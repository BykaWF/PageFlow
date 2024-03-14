package com.project.pageflow.service;

import com.project.pageflow.dto.CartItemDto;
import com.project.pageflow.models.*;
import com.project.pageflow.repository.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.project.pageflow.models.CartItemsStatus.PENDING;

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

        if (cartItemRepository.findCartItemByBookAndStatus(book, PENDING) != null) {
            updateExistingCartItem(book, cartItemDto.getQuantity());
        } else {
            addNewCartItem(cartItemDto, book, currentShoppingSession, cartItemDto.getQuantity());
        }
    }

    private void addNewCartItem(CartItemDto cartItemDto, Book book, ShoppingSession currentShoppingSession, Integer quantity) {
        CartItem cartItem = cartItemDto.toItem();
        cartItem.setBook(book);
        cartItem.setStatus(PENDING);
        cartItem.setShoppingSessionId(currentShoppingSession.getId());
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
        return cartItemRepository.findCartItemByShoppingSessionIdAndStatus(sessionId,PENDING);
    }

    private Book getBookById(Integer bookId) {
        return bookService.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
    }

    private void setSubtotalForBook(CartItem cartItem, Book book) {
        cartItem.setSubtotal(book.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
    }

    public void updateExistingCartItem(Book book, Integer quantity) {
        CartItem cartItemByBook = cartItemRepository.findCartItemByBookAndStatus(book, PENDING);
        cartItemByBook.setQuantity(quantity);
        setSubtotalForBook(cartItemByBook, book);
        cartItemRepository.save(cartItemByBook);
    }

    public void cleanup(Long shoppingSessionId) {
        cartItemRepository.deleteAllByShoppingSessionId(shoppingSessionId);
    }

    public List<CartItem> getPurchasedCartItems(String transactionId) {
        if (transactionId != null) {
            return cartItemRepository.findCartItemByTransactionId(transactionId);
        } else {
            throw new IllegalArgumentException("Transaction id is null");
        }
    }

    public List<CartItem> getPendingCartItems(List<CartItem> cartItems) {
        return cartItems
                .stream()
                .filter(cartItem -> cartItem.getStatus().equals(PENDING))
                .toList();
    }
}
